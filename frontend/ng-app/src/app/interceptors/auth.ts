import {
  HttpHandlerFn,
  HttpInterceptorFn,
  HttpRequest,
} from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn,
) => {
  const cloned = req.clone({
    setHeaders: {
      Authorization: 'Bearer token',
    },
  });
  return next(cloned);
};
