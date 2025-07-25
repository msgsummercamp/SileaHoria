import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const skipUrls = ['https://dog.ceo/api/breeds/image/random'];
  const token = localStorage.getItem('token');

  if (skipUrls.includes(req.url) || token === null) {
    return next(req);
  }

  const cloned = req.clone({
    setHeaders: {
      Authorization: 'Bearer Token: ' + token,
    },
  });

  return next(cloned);
};
