import { Component, inject } from '@angular/core';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatToolbar } from '@angular/material/toolbar';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { AuthDirective } from '../auth.directive';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-toolbar',
  imports: [
    MatButton,
    MatToolbar,
    RouterLink,
    RouterLinkActive,
    AuthDirective,
    MatIconButton,
    MatIcon,
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
