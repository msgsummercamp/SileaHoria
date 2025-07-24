import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public readonly isAuthenticated = signal<boolean>(false);

  public login() {
    this.isAuthenticated.set(true);
  }

  public logout() {
    this.isAuthenticated.set(false);
  }
}
