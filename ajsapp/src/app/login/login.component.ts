import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../api.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;

      this.apiService.login(username, password).subscribe(
        (response) => {
         
          const accessToken = response.accessToken;
          localStorage.setItem('accessToken', accessToken);
          localStorage.setItem('username',username)
          this.router.navigate(['/home']);
        
        
        },
        (error) =>{
          console.error('Login failed:', error);
          alert('Tài khoản hoặc mật khẩu bạn đăng nhập không đúng')
        }
      )
      // Gửi yêu cầu đến API hoặc xử lý đăng nhập
    }
  }
}
