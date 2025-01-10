import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from './request/books.model';
import { BookSearchRequest } from './request/book-search-request.model';
import { UserWithRoles } from './request/role-permission';
import { RolePermission, RolePermissionRequest } from './request/rolepermission.model';


@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8081/api/book/getAll';
  private addUrl = 'http://localhost:8081/api/book/insert';
  private deleteUrl = 'http://localhost:8081/api/book/delete';
  private updateUrl = 'http://localhost:8081/api/book/update';
  private searchUrl = 'http://localhost:8081/api/book/search';
  private loginUrl = 'http://localhost:8081/api/auth/login';
  private permissionUrl = 'http://localhost:8081/permission/getAll';
  private tokenUrl = 'http://localhost:8081/api/book/roles-with-permissions';
  private roleUrl = 'http://localhost:8081/api/exception/all';
  private perUrl = 'http://localhost:8081/api/exception/allpermission';
  private functionUrl = 'http://localhost:8081/api/exception/getAll'; // Replace with your Spring Boot server URL
  private addrolepermissionUrl = 'http://localhost:8081/api/exception/add'; 
  private permissionRole = 'http://localhost:8081/api/exception/permissions';
  private deletepermission = 'http://localhost:8081/api/exception/delete';
  private updateuserwithrole = 'http://localhost:8081/api/exception/update-role';






  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders{
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })
  }
  private getAuthHeaders1(): HttpHeaders{
    return new HttpHeaders({
      'Content-Type': 'application/json',
    })
  }

  getList(): Observable<any[]>{
    return this.http.get<any[]>(this.apiUrl,{ headers: this.getAuthHeaders() });
  }
  
  addBook(book: Book): Observable<any> {
    return this.http.post<any>(`${this.addUrl}`, book, { headers: this.getAuthHeaders() });
  }

  deleteBook(id: number): Observable<any> {
    const body = { id };
    return this.http.request<any>('delete', this.deleteUrl, {body,headers: this.getAuthHeaders()});
}


updateBook(requestBody: { id: number; bookRequest: Book }): Observable<{ id: number; bookRequest: Book }> {
  return this.http.put<{ id: number; bookRequest: Book }>(`${this.updateUrl}`, requestBody, { headers: this.getAuthHeaders() } );
}


  searchBooks(searchRequest: BookSearchRequest): Observable<any> {
    return this.http.post<any>(this.searchUrl, searchRequest,{ headers: this.getAuthHeaders() });
  }

  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    return this.http.post<any>(this.loginUrl, body, { headers: new HttpHeaders().set('Content-Type', 'application/json') });
  }

  getAll(username:string): Observable<any[]>{
    const body = {username}
    return this.http.post<any[]>(this.permissionUrl,body,{ headers: this.getAuthHeaders1() });
  }

  getRolesWithPermissions(): Observable<UserWithRoles[]> {
    return this.http.get<UserWithRoles[]>(this.tokenUrl,{ headers: this.getAuthHeaders() });
  }
  getAllRoles(): Observable<any[]> {
    return this.http.get<any[]>(this.roleUrl,{headers: this.getAuthHeaders1()});
  }

  getAllPermission(): Observable<any[]> {
    return this.http.get<any[]>(this.perUrl,{headers: this.getAuthHeaders1()});
  }

  getFunctionPermissions(): Observable<{ [key: string]: string[] }> {
    return this.http.get<{ [key: string]: string[] }>(this.functionUrl,{headers: this.getAuthHeaders1()});
  }


  addRolePermissions(request: RolePermissionRequest): Observable<RolePermission[]> {
    return this.http.post<RolePermission[]>(this.addrolepermissionUrl, request, { headers: this.getAuthHeaders1(),});
}

getPermissionsByRole(roleName: string): Observable<string[]> {
  const request = { roleName };
  return this.http.post<string[]>(this.permissionRole, request,{ headers: this.getAuthHeaders1(),});
}
deleteRolePermissions( request: RolePermissionRequest): Observable<void> {
  return this.http.delete<void>(this.deletepermission, {
    body: request,  // Dữ liệu body sẽ là request
    headers: this.getAuthHeaders1()  // Thêm header nếu cần thiết
  });
}
updateUserRole( data: { username: string, role: string } ): Observable<any> {
  return this.http.put(this.updateuserwithrole, data,{ headers: this.getAuthHeaders1(),});
}




}
