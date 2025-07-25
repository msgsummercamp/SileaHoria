import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbar } from '@angular/material/toolbar';
import { NgOptimizedImage } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    MatButton,
    MatToolbar,
    NgOptimizedImage,
    RouterLink,
    RouterOutlet,
    RouterLinkActive,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  protected readonly authService = inject(AuthService);

  public readonly title: string = 'ng-app';

  public handleLogout(): void {
    this.authService.logout();
  }
}
