import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private usernameSubject = new BehaviorSubject<string | null>(localStorage.getItem('username'));
  username$ = this.usernameSubject.asObservable();

  setUsername(username: string) {
    localStorage.setItem('username', username);
    this.usernameSubject.next(username);
  }

  private functionNames = [
    { name: 'Home', url: '/home' },
    { name: 'Role', url: '/role' },
    { name: 'Permission', url: '/token' }
  ];

  getFunctionNames() {
    return this.functionNames;
  }

  setFunctionNames(newFunctions: { name: string; url: string; }[]) {
    this.functionNames = newFunctions;
  }
}
