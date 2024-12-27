export interface Permission {
  name: string;
  isChecked: boolean;
}

export interface FunctionItem {
  name: string;
  isChecked: boolean;
  permissions: Permission[];
}

  