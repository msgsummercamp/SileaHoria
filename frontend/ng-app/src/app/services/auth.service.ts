import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private router = inject(Router);

  public readonly loggedIn = signal(false);

  public login() {
    this.loggedIn.set(true);
    this.router.navigate(['/home']);
  }

  public logout() {
    this.loggedIn.set(false);
  }
}
