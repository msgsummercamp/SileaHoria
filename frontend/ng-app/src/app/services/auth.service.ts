import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly _loggedIn = signal<boolean>(false);

  public readonly loggedIn = this._loggedIn.asReadonly();

  public login() {
    this._loggedIn.set(true);
  }

  public logout() {
    this._loggedIn.set(false);
  }

  public isAuthenticated(): boolean {
    return this._loggedIn();
  }
}
