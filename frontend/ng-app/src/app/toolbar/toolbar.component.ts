import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbar } from '@angular/material/toolbar';
import { NgOptimizedImage } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { AuthDirective } from '../auth.directive';

@Component({
  selector: 'app-toolbar',
  imports: [
    MatButton,
    MatToolbar,
    NgOptimizedImage,
    RouterLink,
    RouterLinkActive,
    AuthDirective,
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss',
})
export class ToolbarComponent {
  protected readonly authService = inject(AuthService);

  public handleLogout(): void {
    this.authService.logout();
  }
}
