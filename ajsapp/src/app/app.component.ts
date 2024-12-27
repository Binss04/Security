import { Component, OnInit, ViewChild } from '@angular/core';
import { ApiService } from './api.service';
import { NgFor } from '@angular/common';
import { Book } from './request/books.model';
import { HttpErrorResponse } from '@angular/common/http';
import { BookSearchRequest } from './request/book-search-request.model';
import { query } from '@angular/animations';
import { Route, Router } from '@angular/router';

declare var bootstrap: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent  {
  title = 'ajsapp';
  username: string | null = null;
  constructor(private router: Router){}

  ngOnInit(): void {
      this.username = localStorage.getItem('username');
  }

  logout(){
    localStorage.removeItem('accessToken');
    localStorage.removeItem('username');
    this.router.navigate(['/login'])
  }
}