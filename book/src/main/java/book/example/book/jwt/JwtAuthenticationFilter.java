package book.example.book.jwt;

import book.example.book.enity.Function;
import book.example.book.enity.Permission;
import book.example.book.repository.FunctionRepository;
import book.example.book.repository.PermissionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final PermissionRepository permissionRepository;
    private final FunctionRepository functionRepository;
    private Map<String, String> permissionMap;

    private Map<String, String> functionMap;

    //Constructor
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, PermissionRepository permissionRepository, FunctionRepository functionRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.permissionRepository = permissionRepository;
        this.functionRepository = functionRepository;
    }

    private void loadPermissionsFromDB(String username){
        List<Permission> permissions = permissionRepository.findPermissionsByUsername(username);
        permissionMap = permissions.stream()
                .collect(Collectors.toMap(
                        Permission::getEndpoint,
                        Permission::getName
                ));

    }

    private void loadFunctionFromDB(String username){
        List<Function> functions = functionRepository.findFunctionByUsername(username);
        functionMap = functions.stream()
                .collect(Collectors.toMap(
                        Function::getUrl,
                        Function::getName
                ));

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Get JWT token from HTTP request
        try {
            String token = getTokenFromRequest(request);
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                setAuthentication(request, userDetails);

                if (!hasPermission(request)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return;
                }
//                if (!hasFunction(request)) {
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//                    return;
//                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed: " + ex.getMessage());
        }
    }
    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
//    kiểm tra quyền truy cập
    private boolean hasPermission(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        String username = jwtTokenProvider.getUsername(token);
        loadPermissionsFromDB(username);
        String requestURI = request.getRequestURI();
        String requiredPermission = permissionMap.get(requestURI);
        System.out.println(permissionMap);
        System.out.println(requiredPermission);
        if (requiredPermission == null) {
            return false;
        }
        return  true;
    }
// Kiểm tra quyền truy cập cua Function
//    private boolean hasFunction(HttpServletRequest request) {
//        String token = getTokenFromRequest(request);
//        String username = jwtTokenProvider.getUsername(token);
//        loadFunctionFromDB(username);
//        String requestURI = request.getRequestURI();
//        String requiredFuction = functionMap.get(requestURI);
//        System.out.println(functionMap);
//        System.out.println(requiredFuction);
//        if (requiredFuction == null) {
//            return false;
//        }
//        return  true;
//    }


    // Extract the token
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
