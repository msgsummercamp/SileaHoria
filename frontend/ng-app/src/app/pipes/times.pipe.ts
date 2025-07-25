import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'times',
})
export class TimesPipe implements PipeTransform {
  public transform(value: number): string {
    return value.toString() + ' ' + (value === 1 ? 'time' : 'times');
  }
}
