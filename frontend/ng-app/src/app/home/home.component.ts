import { Component, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatButton } from '@angular/material/button';

type DogResponse = {
  status: string;
  message: string;
};

@Component({
  selector: 'app-home',
  imports: [MatButton],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  private readonly httpClient: HttpClient = inject(HttpClient);

  public readonly data = signal<string>('');
  public readonly loading = signal<boolean>(false);
  public readonly error = signal<string>('');

  public loadRandomDogImage(): void {
    this.loading.set(true);
    this.error.set('');

    this.httpClient
      .get<DogResponse>('https://dog.ceo/api/breeds/image/random')
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
        error: (err: Error) => {
          this.data.set('');
          this.error.set('Error loading image: ' + err.message);
          this.loading.set(false);
        },
      });
  }
}
