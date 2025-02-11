export interface Permission {
    roleName: string; // Tên của Role (Chức vụ)
    permissions: string[]; // Danh sách các quyền liên quan đến Role
  }
  
  export interface UserWithRoles {
    selectedRole: any;
    
    map(arg0: (permission: any) => { name: any; isChecked: boolean; }): any;
    username: string; // Tên người dùng
    roles: Permission[]; // Danh sách Role và các Permission liên quan
    
  }
  export interface Role {
    id?: number; 
    name: string;
    
  }

  
  