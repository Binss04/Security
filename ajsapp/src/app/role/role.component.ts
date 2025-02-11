import { Component } from '@angular/core';
import { ApiService } from '../api.service';
import { Role, UserWithRoles } from '../request/role-permission';
import $ from 'jquery';


@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrl: './role.component.css'
})
export class RoleComponent {
  roles: String[] = [];
  usersWithRoles: UserWithRoles[] = [];
  isFormVisible: boolean = false;
  roleName: string = '';
  role: Role = { name: '' }; // Khởi tạo object Role
  response: Role | null = null;
   

  constructor(private apiService: ApiService){}
  ngOnInit(): void{
    this.loadUsersWithRoles();
    this.loadRoles()

  }
  loadRoles(): void {
    this.apiService.getAllRoles().subscribe({
      next: (data) => (this.roles = data),
      error: (err) => console.error('Error fetching roles', err),
    });
  }

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
  updateUserRole(user: UserWithRoles): void {
    if (user.selectedRole) {
      // Chỉ cần username và selectedRole để gửi request cập nhật role
      const updateRequest = {
        username: user.username,
        role: user.selectedRole
      };
      // Gọi API để cập nhật vai trò cho người dùng
      this.apiService.updateUserRole(updateRequest).subscribe(
        (response) => {
          console.log('Cập nhật role thành công:', response);
          alert('Cập nhật role thành công');
        },
        (error) => {
          console.error('Lỗi khi cập nhật role:', error);
          alert('Cập nhật role không thành công');
        }
      );
    } else {
      alert('Vui lòng chọn role');
    }
  }


  onSubmit() {
    this.apiService.addRole(this.role).subscribe({
      next: (data) => {
        console.log('Role added successfully:', data);
        this.response = data;
        alert('Thêm thành công')
      },  
      error: (error) => {
        console.error('Error adding role:', error);
        if (error.status === 400) {
          alert(error.error); // Lấy thông báo lỗi từ backend
        } else {
          alert('Có lỗi xảy ra, vui lòng thử lại');
        }
      },
    });
  }
}


  

