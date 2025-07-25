import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public readonly isLoggedIn = signal(false);

  private router = inject(Router);

  public login() {
    this.isLoggedIn.set(true);
    this.router.navigate(['/home']);
  }

  public logout() {
    this.isLoggedIn.set(false);
  }
}
