import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ApiService } from '../api.service';
import { UserWithRoles } from '../request/role-permission';
import { forkJoin, of } from 'rxjs';
import { RolePermissionRequest } from '../request/rolepermission.model';


declare var bootstrap: any;




@Component({
  
  selector: 'app-token',
  templateUrl: './token.component.html',
  styleUrl: './token.component.css'
})
export class TokenComponent implements OnInit {
  @ViewChild('assignRoleModal') assignRoleModal!: ElementRef;
  @Input() functionPermissions: { [key: string]: string[] } = {};
  selectedFunctions: { [key: string]: boolean } = {}; // Theo dõi trạng thái checkbox của các function
  selectedPermissions: { [functionName: string]: { [permission: string]: boolean } } = {};
  add: string[] = [];

  delete: string[] = [];
  originPermission: string[] = [];
  existingPermissions: string[] = []; // Danh sách quyền đã tồn tại
  @Input() roles: string[] = []; // Danh sách role
  permissions: string[] = []; // Danh sách permissioss
  selectedRoleName: string = ''; // Role được chọn
  usersWithRoles: UserWithRoles[] = [];
  rolePermissions: { [key: string]: any } = {};
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
  
  // getAllUser
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

  updateSelectedPermissions(): void {
    Object.keys(this.selectedPermissions).forEach((functionName) => {
      Object.keys(this.selectedPermissions[functionName]).forEach((permission) => {
        // Đánh dấu quyền là được chọn nếu nó có trong existingPermissions
        this.selectedPermissions[functionName][permission] = this.existingPermissions.includes(permission);
      });
    });
  }
  
submit(): void {
  const addRequest: RolePermissionRequest = {
        roleName: this.selectedRoleName,
        permissionNames: this.add,
      };
  this.apiService.addRolePermissions(addRequest).subscribe()
  const deleteRequest: RolePermissionRequest = {
    roleName: this.selectedRoleName,
    permissionNames: this.delete,
  };
  this.apiService.deleteRolePermissions(deleteRequest).subscribe()

  alert('Cập nhật thành công!')
  this.loadUsersWithRoles();
  const modalElement = this.assignRoleModal.nativeElement;
  const modal = bootstrap.Modal.getInstance(modalElement);
  if (modal) {
    modal.hide();
  }
  
  };

  
  //call api function
  // Xử lý thay đổi chức năng
  toggleFunction(functionName: string): void {
    const isChecked = this.selectedFunctions[functionName];
    if (!isChecked) {
      // Nếu bỏ tích function, bỏ tích tất cả các permission trong nhóm đó
      Object.keys(this.selectedPermissions[functionName]).forEach(permission => {
        this.selectedPermissions[functionName][permission] = false;
      });
    }
  }
  
  loadFunction(): void {
    this.apiService.getFunctionPermissions().subscribe(data => {
      this.functionPermissions = data;
  
      // Khởi tạo trạng thái mặc định
      Object.keys(data).forEach(func => {
        this.selectedFunctions[func] = false; // Mặc định là chưa tích function
        this.selectedPermissions[func] = {}; // Tạo một đối tượng riêng cho mỗi nhóm
        data[func].forEach(permission => {
          this.selectedPermissions[func][permission] = false; // Mặc định là chưa tích permission
        });
      });
    });
  }
  


  onPermissionChange(functionName: string, permission: string): void {
    const isChecked = this.selectedPermissions[functionName][permission];
    if (isChecked === false && this.originPermission.includes(permission)){
      this.delete.push(permission)
    }else if(isChecked === false) {
      this.add = this.add.filter(item => item !== permission)
    }

    if (isChecked === true && this.originPermission.includes(permission)){
      this.delete = this.delete.filter(item => item !== permission);
    }else if(isChecked === true) {
      this.add.push(permission)
    }
  }

 

  onRoleChange(): void {
    if (this.selectedRoleName) {
      this.apiService.getPermissionsByRole(this.selectedRoleName).subscribe(permissions => {
        // Reset tất cả trạng thái trước khi cập nhật
        this.originPermission = permissions
        Object.keys(this.selectedPermissions).forEach(func => {
          this.selectedFunctions[func] = false;
          Object.keys(this.selectedPermissions[func]).forEach(permission => {
            this.selectedPermissions[func][permission] = false;
          });
        });
        // Cập nhật trạng thái dựa trên permissions được trả về
        permissions.forEach(permission => {
          // Tìm function liên quan đến permission này
          Object.keys(this.selectedPermissions).forEach(func => {
            if (this.selectedPermissions[func][permission] !== undefined) {
              this.selectedPermissions[func][permission] = true;
              this.selectedFunctions[func] = true;
             }
          });
        });
      });
    }
  }
  }
  
 

  

  

   

