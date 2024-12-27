import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoggedInGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.router.navigate(['/home']); // Nếu đã đăng nhập, chuyển hướng tới /home
      return false; // Ngăn không cho truy cập vào /login
    }
    return true; // Nếu chưa đăng nhập, cho phép truy cập
  }
}
