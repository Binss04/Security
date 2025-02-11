import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './login/auth.service';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  functionNames: string[] = []; // Danh sách quyền của user

  constructor(private router: Router,private apiService: ApiService, private authService: AuthService) {}

  // canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
  //   const token = localStorage.getItem('accessToken');
  //   if (token) {
  //     return true; // If token exists, allow access
  //   } else {
  //     this.router.navigate(['/login']); // Redirect to login if no token
  //     return false;
  //   }
   
  // }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const token = localStorage.getItem('accessToken');
    if (!token) {
      this.router.navigate(['/login']); // Nếu không có token, chuyển hướng về trang đăng nhập
      return false;
    }

    return new Promise((resolve) => {
      const username = localStorage.getItem('username'); // Lấy username từ localStorage
      if (!username) {
        this.router.navigate(['/login']);
        resolve(false);
      }

      this.apiService.getRolesFunctionsPermissions(username!).subscribe(
        (data) => {
          this.functionNames = Object.values(data).flat().map((func: any) => func.url); // Lấy danh sách URL hợp lệ

          if (this.functionNames.includes(state.url)) {
            resolve(true); // Nếu có quyền, cho phép truy cập
          } else {
            this.router.navigate(['/not-found']); // Nếu không có quyền, điều hướng đến trang lỗi
            resolve(false);
          }
        },
        (error) => {
          console.error('Error fetching permissions:', error);
          this.router.navigate(['/login']);
          resolve(false);
        }
      );
    });
  }
    

 
}
