import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbar } from '@angular/material/toolbar';
import { NgOptimizedImage } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatButton, MatToolbar, NgOptimizedImage, RouterLink, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  public readonly loggedIn = signal<boolean>(false);

  public readonly title: string = 'ng-app';

  public handleLogin(): void {
    this.loggedIn.set(!this.loggedIn());
  }
}
