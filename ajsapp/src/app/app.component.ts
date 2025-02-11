import { Component, OnInit, ViewChild } from '@angular/core';
import { ApiService } from './api.service';
import { NgFor } from '@angular/common';
import { Book } from './request/books.model';
import { HttpErrorResponse } from '@angular/common/http';
import { BookSearchRequest } from './request/book-search-request.model';
import { query } from '@angular/animations';
import { Route, Router } from '@angular/router';
import { Functions } from './request/function.model';
import { AuthService } from './login/auth.service';

declare var bootstrap: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent  {
  rolesFunctionsPermissions: any = {};
  roleFunctions: { [role: string]: string[] } = {};
  functionNames: Functions[] = [];
  title = 'ajsapp';
  username: string | null = null;
  constructor(private router: Router,private apiService: ApiService, private authService: AuthService){}

  ngOnInit(): void {
    this.authService.username$.subscribe(username => {
      this.username = username;
      if (this.username) {
        this.getRolesFunctionsPermissions(this.username);
      }
    });
  }
  

  logout(){
    localStorage.removeItem('accessToken');
    localStorage.removeItem('username');
    this.router.navigate(['/login'])
  }

  getRolesFunctionsPermissions(username: string | null): void {
    if (username) {
      this.apiService.getRolesFunctionsPermissions(username).subscribe(
        (data) => {
          console.log('Data from API:', data);
  
          // Extract values from the object and flatten them into an array
          this.functionNames = Object.values(data).flat() as Functions[];
          
          console.log('Processed functionNames:', this.functionNames);
        },
        (error) => {
          console.error('Error calling API:', error);
          this.functionNames = [];
        }
      );
    }
  }
  

  
  }


  

