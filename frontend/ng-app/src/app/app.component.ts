import { Component, inject, signal, WritableSignal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbar} from "@angular/material/toolbar";
import { NgOptimizedImage } from "@angular/common";
import { HttpClient } from "@angular/common/http";
import { NotFoundComponent } from "./not-found/not-found.component";
import {AuthDirective} from "./auth.directive";

type DogResponse = {
  status: string;
  message: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatButton, MatToolbar, NgOptimizedImage, NotFoundComponent, AuthDirective],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  private readonly httpClient: HttpClient = inject(HttpClient);

  public readonly data: WritableSignal<string> = signal('');
  public readonly loading: WritableSignal<boolean> = signal(false);
  public readonly error: WritableSignal<string> = signal('');
  public readonly loggedIn: WritableSignal<boolean> = signal(false);

  public readonly title: string = 'ng-app';

  public handleLogin(): void {
    this.loggedIn.set(!this.loggedIn());
  }

  public loadRandomDogImage(): void {
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
        error: (err: Error) => {
          this.data.set('');
          this.error.set('Error loading image: ' + err.message);
          this.loading.set(false);
        }
      });
  }
}
