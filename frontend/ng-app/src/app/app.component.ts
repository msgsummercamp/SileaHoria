import { Component } from '@angular/core';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatButton],
  template: '<button mat-raised-button>CLICK!</button>',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'ng-app';
}
