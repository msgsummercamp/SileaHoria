import {Directive, ElementRef, inject, input, InputSignal} from '@angular/core';
import {toObservable} from "@angular/core/rxjs-interop";

@Directive({
  selector: '[auth]'
})
export class AuthDirective {
  private readonly hostElement: ElementRef<HTMLElement> = inject(ElementRef);

  public readonly authStatus: InputSignal<boolean> = input.required<boolean>();

  constructor() {
    toObservable(this.authStatus).subscribe(authStatus => {
      if (authStatus) {
        this.hostElement.nativeElement.classList.remove('hidden');
      } else {
        this.hostElement.nativeElement.classList.add('hidden');
      }
    });
  }
}
