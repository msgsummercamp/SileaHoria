import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const skipUrls = ['https://dog.ceo/api/breeds/image/random'];

  if (skipUrls.includes(req.url)) {
    return next(req);
  }

  const cloned = req.clone({
    setHeaders: {
      Authorization: 'Bearer token',
    },
  });

  return next(cloned);
};
