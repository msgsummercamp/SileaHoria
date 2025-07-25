import { Component, inject, Signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { AuthService } from '../services/auth.service';
import { AuthDirective } from '../auth.directive';
import {
  FormControl,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {
  MatError,
  MatFormField,
  MatInput,
  MatLabel,
} from '@angular/material/input';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardFooter,
  MatCardHeader,
  MatCardTitle,
} from '@angular/material/card';

type LogInForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login',
  imports: [
    MatButton,
    AuthDirective,
    ReactiveFormsModule,
    MatInput,
    MatFormField,
    MatLabel,
    MatError,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    MatCardContent,
    MatCardActions,
    MatCardFooter,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private readonly authService = inject(AuthService);
  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected readonly loginFormGroup = this.formBuilder.group<LogInForm>({
    email: this.formBuilder.control('', [
      Validators.required,
      Validators.email,
    ]),
    password: this.formBuilder.control('', [
      Validators.required,
      Validators.minLength(6),
    ]),
  });

  public readonly loggedIn: Signal<boolean> = this.authService.loggedIn;

  public onFormSubmit() {
    if (this.loginFormGroup.valid) {
      this.handleLogin();
      console.log(this.loginFormGroup.getRawValue());
    }
  }

  public handleLogin() {
    this.authService.login();
  }

  protected readonly toString = toString;
}
