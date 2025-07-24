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
  private readonly hostElement: ElementRef<HTMLElement> = inject(ElementRef);

  public readonly authStatus: InputSignal<boolean> = input.required<boolean>();

  constructor() {
    effect(() => {
      this.hostElement.nativeElement.hidden = !this.authStatus();
    });
  }
}
