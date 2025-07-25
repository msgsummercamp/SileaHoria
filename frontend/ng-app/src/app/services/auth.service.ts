import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

type responseType = {
  token: string;
  user: {
    id: number;
    role: string;
  };
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public readonly isLoggedIn = signal(false);

  private readonly router = inject(Router);
  private readonly http = inject(HttpClient);
  private readonly cookieService = inject(CookieService);
  private readonly API_URL = 'http://localhost:8080';

  constructor() {
    if (this.cookieService.get('token') != '') {
      this.isLoggedIn.set(true);
      this.router.navigate(['/home']);
    }
  }

  public login(username: string, password: string) {
    this.http
      .post<responseType>(this.API_URL + '/auth/login', { username, password })
      .subscribe({
        next: (response) => {
          this.cookieService.set('token', response.token);
          this.isLoggedIn.set(true);
          this.router.navigate(['/home']);
        },
        error: (err: Error) => {
          console.error('Login failed:', err.message);
        },
      });
  }

  public logout() {
    this.cookieService.delete('token');
    this.isLoggedIn.set(false);
  }

  public getToken() {
    return this.cookieService.get('token');
  }
}
