import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'infinity',
})
export class InfinityPipe implements PipeTransform {
  transform(value: string): string {
    if (value === '8') {
      return '∞';
    }
    return value;
  }
}
