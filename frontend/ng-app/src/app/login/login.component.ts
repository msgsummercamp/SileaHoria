import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { AuthService } from '../services/auth.service';
import { AuthDirective } from '../auth.directive';

@Component({
  selector: 'app-login',
  imports: [MatButton, AuthDirective],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private authService: AuthService = inject(AuthService);

  public readonly loggedIn = this.authService.loggedIn;

  public handleLogin() {
    this.authService.login();
  }

  public handleLogout() {
    this.authService.logout();
  }
}
