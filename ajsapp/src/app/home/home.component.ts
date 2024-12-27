import { Component, OnInit, ViewChild } from '@angular/core';
import { ApiService } from '../api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { FormGroup, NgForm } from '@angular/forms';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Book } from '../request/books.model';
import { Permission } from '../request/permission.model';
import { BookSearchRequest } from '../request/book-search-request.model';


declare var bootstrap: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

 @ViewChild('editBookModal') editBookModal!: Element;
 @ViewChild('bookForm') bookForm!: NgForm;
 isFormValid: boolean = true;
 userRole: string = ''; 
 username: string = '';
 add: boolean = false;
 delete: boolean = false;
 search: boolean = false;
 update: boolean = false;
 
  totalItems: number = 0;
  books: Book[] = [];
  permission: Permission[] = [];
  newBook: Book = new Book(0,'','','','',0,0,new Date(),'','');
  selectedBook: Book = {
    id : 0,
    title: '',
    author: '',
    genre: '',
    language: '',
    pagecount: 0,
    price: 0,
    publish_date:new Date(),
    publisher: '',
    synopsis: '',
  };
  searchRequest: BookSearchRequest = {
    author: '',
    genre: '',
    language: '',
    startDate: null,
    endDate: null,
    pageNo: 1,
    pageSize: 5,
    id: 0
  };
  Math = Math;
  title = 'ajsapp';
  // private jwtHelper: JwtHelperService
  constructor(private apiService: ApiService){}

  ngOnInit(): void {
    this.searchBooks();
   this.getPermission();
  }
  validateForm(): boolean {
    // Kiểm tra các trường trống
    return !!this.selectedBook.title && !!this.selectedBook.author && !!this.selectedBook.genre &&
           !!this.selectedBook.language && this.selectedBook.pagecount > 0 &&
           this.selectedBook.price > 0 && !!this.selectedBook.publish_date &&
           !!this.selectedBook.publisher && !!this.selectedBook.synopsis;
  }


 
  addBook(): void{
    if(this.bookForm.valid){
    this.apiService.addBook(this.newBook).subscribe(
      (book: any) => {
        console.log(book)
        this.searchBooks()
        alert('Thêm thành công!')
      },
      (error: HttpErrorResponse) => {
          console.error('Error adding book', error);
          alert('Bạn không có quyền sử dụng chức năng này!');
        }
    );
    }else{
      alert('Vui lòng điển đầy đủ thông tin!')
    }
  }
  deleteBook(id: number) {
    this.apiService.deleteBook(id).subscribe(
      (response: any) => {
        if (typeof response === 'string') {
          alert(response);
          this.books = []; 
        } else {
          
          this.searchBooks()
          alert('Xóa thành công!');
        }
      },
      (error: HttpErrorResponse) => {
        console.error('Error deleting book', error);
        alert('Bạn không có quyền sử dụng chức năng này!');
      }
    );
  }
 
  
  
  openEditBookModal(book: Book) {
    this.selectedBook = { ...book }; 
    const modal = document.getElementById('editBookModal'); 
    if (modal) {
      const bootstrapModal = new bootstrap.Modal(modal); 
      bootstrapModal.show();
    }
  }
  
  

  editBook(book: Book) {
   
    this.selectedBook = { ...book }; 
  }

  
  
  updateBook(): void {
    this.isFormValid = this.validateForm();
  
    if (!this.isFormValid) {
      alert('Vui lòng điền đầy đủ thông tin!');
      return;
    }
  
    const requestBody = {
      id: this.selectedBook.id,
      bookRequest: this.selectedBook 
    };
  
    console.log('Request Body:', requestBody);

    this.apiService.updateBook(requestBody).subscribe(
      (response: { id: number; bookRequest: Book }) => {
        console.log(response);
        alert('Sửa thành công!');
        this.selectedBook = response.bookRequest; // Cập nhật selectedBook trực tiếp
        this.searchBooks(); 
      },
      (error: HttpErrorResponse) => {
        console.error('Error updating book', error);
      }
    );
  }
  
  cancelEdit() {
    this.selectedBook = new Book(0,'','','','',0,0,new Date(),'',''); // Reset lại biến khi hủy sửa
  }

  searchBooks(): void {
    this.apiService.searchBooks(this.searchRequest).subscribe(response => {
      this.books = response['b_out_cursor']; 
      this.totalItems = response['b_total'];
  
      // Check if the books array is empty or undefined
      if (!this.books || this.books.length === 0) {
        alert('Không tìm thấy sách nào phù hợp với tìm kiếm của bạn.');
      }
    }, error => {
      console.error('Error fetching books:', error);
      alert('Đã xảy ra lỗi khi tìm kiếm sách. Vui lòng thử lại sau.');
    });
  }

  getPermission(): void {
    const token = localStorage.getItem('username'); // Retrieve token from localStorage

    if (token) {
      this.username = token; // Assign token to username
      this.apiService.getAll(this.username).subscribe(response => {
        this.permission = response; // Assign API response to permission
        this.add = false
        this.update = false
        this.search = false
        this.delete = false
        for(const perm of this.permission){
          if(perm.name === 'INSERT_BOOK'){
            
            this.add = true;
          }
          else if(perm.name === 'DELETE_BOOK'){
            this.delete = true;
          }
          else if(perm.name === 'UPDATE_BOOK'){
            this.update = true;
          }
          else{
            this.search = true;
          }
        }
      });
    } else {
      console.error('No username found in localStorage');
    }
    
  }
  

  onSearchClick(): void {
    this.searchRequest.pageNo = 1;  // Reset pageNo về 1 khi thực hiện tìm kiếm
    this.searchBooks();  // Thực hiện tìm kiếm lại với pageNo = 1
  }

  onPageChange(page: number): void {
    if (page > 0 && page <= Math.ceil(this.totalItems / this.searchRequest.pageSize)) {
      this.searchRequest.pageNo = page;  // Cập nhật trang hiện tại
      this.searchBooks();  // Tìm kiếm lại với pageNo mới
    }
  }

  

 

}
