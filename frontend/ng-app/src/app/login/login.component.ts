import { Component, inject, Signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { AuthService } from '../services/auth.service';
import { AuthDirective } from '../auth.directive';
import {
  FormControl,
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

type LogInForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login',
  imports: [MatButton, AuthDirective, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private readonly authService = inject(AuthService);
  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected readonly loginFormGroup: FormGroup =
    this.formBuilder.group<LogInForm>({
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

  public handleLogout() {
    this.authService.logout();
  }
}
