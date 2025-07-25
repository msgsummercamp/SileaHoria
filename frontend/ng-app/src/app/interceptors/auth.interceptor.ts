import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const skipUrls = ['https://dog.ceo/api/breeds/image/random'];
  const shouldSkip = skipUrls.includes(req.url);

  if (shouldSkip) {
    return next(req);
  }

  const cloned = req.clone({
    setHeaders: {
      Authorization: 'Bearer token',
    },
  });

  return next(cloned);
};
