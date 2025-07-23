import {Component, inject, signal, WritableSignal} from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbar} from "@angular/material/toolbar";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {HttpClient} from "@angular/common/http";

type DogResponse = {
  status: string;
  message: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatButton, MatToolbar, NgOptimizedImage, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title: string = 'ng-app';

  data: WritableSignal<string> = signal('');
  loading: WritableSignal<boolean> = signal(false);
  error: WritableSignal<string> = signal('');
  httpClient: HttpClient = inject(HttpClient);

  loadRandomDogImage(): void {
    this.loading.set(true);
    this.error.set('');
    this.httpClient
      .get<DogResponse>("https://dog.ceo/api/breeds/image/random")
      .subscribe({
        next: (response: DogResponse) => {
          if (response.status === 'success') {
            this.data.set(response.message);
            this.error.set('');
          } else {
            this.data.set('');
            this.error.set('Failed to load dog image.');
          }
          this.loading.set(false);
        },
        error: () => {
          this.data.set('');
          this.error.set('Error loading image...');
          this.loading.set(false);
        }
      });
  }
}
