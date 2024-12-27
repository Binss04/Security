import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { UserWithRoles } from '../request/role-permission';
import { FunctionItem } from '../request/function';

@Component({
  selector: 'app-token',
  templateUrl: './token.component.html',
  styleUrl: './token.component.css'
})
export class TokenComponent implements OnInit {
  functionPermissions: { [key: string]: string[] } = {};
  selectedFunctions: { [key: string]: boolean } = {}; // Theo dõi trạng thái checkbox của các function
  selectedPermissions: { [key: string]: boolean } = {};
  roles: string[] = []; // Danh sách role
  permissions: string[] = []; // Danh sách permissioss
  selectedRoleName: string = ''; // Role được chọn
  // selectedPermissions: string[] = []; // Danh sách permission được chọn
  usersWithRoles: UserWithRoles[] = [];
  isLoading = true;
  constructor(private apiService: ApiService) {}
  ngOnInit(): void {
    this.loadUsersWithRoles();
    this.loadRoles();
    this.loadPermission();
    this.loadFunction();
    
  }
  loadRoles(): void {
    this.apiService.getAllRoles().subscribe({
      next: (data) => (this.roles = data),
      error: (err) => console.error('Error fetching roles', err),
    });
  }
  loadPermission(): void {
    this.apiService.getAllPermission().subscribe({
      next: (data) => (this.permissions = data),
      error: (err) => console.error('Error fetching roles', err),
    });
  }
  // togglePermission(permission: string, event: any) {
  //   if (event.target.checked) {
  //     this.selectedPermissions.push(permission);
  //   } else {
  //     this.selectedPermissions = this.selectedPermissions.filter(
  //       (perm) => perm !== permission
  //     );
  //   }
  // }
  // getAllRoles
  loadUsersWithRoles(): void {
    this.apiService.getRolesWithPermissions().subscribe(
      (data: UserWithRoles[]) => {
        this.usersWithRoles = data;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }
  submit(){
  }
  

  //call api function
  toggleFunction(functionName: string): void {
    const isChecked = this.selectedFunctions[functionName];
    if (!isChecked) {
      // Nếu bỏ tích function, bỏ tích các permission liên quan
      this.functionPermissions[functionName].forEach(permission => {
        this.selectedPermissions[permission] = false;
      });
    }
  }
  loadFunction(): void{
    this.apiService.getFunctionPermissions().subscribe(data => {
      this.functionPermissions = data;
      // Khởi tạo trạng thái checkbox
      Object.keys(data).forEach(func => {
        this.selectedFunctions[func] = false;
        data[func].forEach(permission => {
          this.selectedPermissions[permission] = false;
        });
      });
    });
  }
  
  }
  
 

  

  

   

