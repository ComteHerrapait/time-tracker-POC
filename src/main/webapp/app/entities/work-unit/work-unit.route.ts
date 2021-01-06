import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkUnit, WorkUnit } from 'app/shared/model/work-unit.model';
import { WorkUnitService } from './work-unit.service';
import { WorkUnitComponent } from './work-unit.component';
import { WorkUnitDetailComponent } from './work-unit-detail.component';
import { WorkUnitUpdateComponent } from './work-unit-update.component';

@Injectable({ providedIn: 'root' })
export class WorkUnitResolve implements Resolve<IWorkUnit> {
  constructor(private service: WorkUnitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkUnit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workUnit: HttpResponse<WorkUnit>) => {
          if (workUnit.body) {
            return of(workUnit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkUnit());
  }
}

export const workUnitRoute: Routes = [
  {
    path: '',
    component: WorkUnitComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WorkUnits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkUnitDetailComponent,
    resolve: {
      workUnit: WorkUnitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WorkUnits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkUnitUpdateComponent,
    resolve: {
      workUnit: WorkUnitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WorkUnits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkUnitUpdateComponent,
    resolve: {
      workUnit: WorkUnitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WorkUnits',
    },
    canActivate: [UserRouteAccessService],
  },
];
