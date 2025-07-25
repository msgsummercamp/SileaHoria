import {
  Directive,
  effect,
  ElementRef,
  inject,
  input,
  InputSignal,
} from '@angular/core';

@Directive({
  selector: '[authStatus]',
})
export class AuthDirective {
  public readonly authStatus: InputSignal<boolean> = input.required<boolean>();

  private readonly hostElement: ElementRef<HTMLElement> = inject(ElementRef);

  constructor() {
    effect(() => {
      const loggedIn: boolean = this.authStatus();

      this.hostElement.nativeElement.hidden = !loggedIn;
    });
  }
}
