import { HttpEventType, HttpInterceptorFn } from '@angular/common/http';
import { tap } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const cloned = req.clone({
    setHeaders: {
      Authorization: 'Bearer token',
    },
  });
  return next(cloned).pipe(
    tap((event) => {
      if (event.type === HttpEventType.Response) {
        if (event.ok) {
          console.log('Request successful:', event);
        } else {
          console.error('Request failed:', event);
        }
      }

      return event;
    }),
  );
};
