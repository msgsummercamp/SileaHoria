import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public readonly isLoggedIn = signal(false);

  private readonly router = inject(Router);
  private readonly http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080';

  public login(username: string, password: string) {
    this.http
      .post(this.API_URL + '/auth/login', { username, password })
      .subscribe({
        next: (response: any) => {
          console.log('Login successful:', response);
          localStorage.setItem('token', response.token);
          this.isLoggedIn.set(true);
          this.router.navigate(['/home']);
        },
        error: (err: Error) => {
          console.error('Login failed:', err.message);
        },
      });
  }

  public logout() {
    localStorage.removeItem('token');
    this.isLoggedIn.set(false);
  }
}
