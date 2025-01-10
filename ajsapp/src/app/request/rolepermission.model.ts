export interface RolePermissionRequest {
    roleName: string;
    permissionNames: string[];
  }
  
  
  
  // Interface cho RolePermission (response)
  export interface RolePermission {
    id: number;
    role: {
      id: number;
      name: string;
    };
    permission: {
      id: number;
      name: string;
      endpoint: string;
    };
  }