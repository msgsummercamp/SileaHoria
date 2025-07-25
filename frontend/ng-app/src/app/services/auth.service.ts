import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public readonly loggedIn = signal<boolean>(false);

  public login() {
    this.loggedIn.set(true);
  }

  public logout() {
    this.loggedIn.set(false);
  }
}
