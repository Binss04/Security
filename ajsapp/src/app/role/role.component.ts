import { Component } from '@angular/core';
import { ApiService } from '../api.service';
import { UserWithRoles } from '../request/role-permission';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrl: './role.component.css'
})
export class RoleComponent {
  roles: String[] = [];
  usersWithRoles: UserWithRoles[] = [];
   

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
  
}
