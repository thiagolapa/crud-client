import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { ClientComponent } from './client.component';
import { ClientDetailComponent } from './client-detail.component';
import { ClientSimulationComponent } from './client-simulation.component';
import { ClientUpdateComponent } from './client-update.component';
import { ClientDeletePopupComponent } from './client-delete-dialog.component';
import { IClient } from 'app/shared/model/client.model';
import { JhiResolvePagingParams } from 'ng-jhipster';

@Injectable({ providedIn: 'root' })
export class ClientResolve implements Resolve<IClient> {
  constructor(private service: ClientService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClient> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((client: HttpResponse<Client>) => client.body));
    }
    return of(new Client());
  }
}

export const clientRoute: Routes = [
  {
    path: '',
    component: ClientComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customersApp.client.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClientDetailComponent,
    resolve: {
      client: ClientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customersApp.client.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClientUpdateComponent,
    resolve: {
      client: ClientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customersApp.client.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClientUpdateComponent,
    resolve: {
      client: ClientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customersApp.client.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
   {
     path: ':id/simulation',
     component: ClientSimulationComponent,
     resolve: {
       client: ClientResolve
     },
     data: {
       authorities: ['ROLE_USER'],
       pageTitle: 'customersApp.client.home.title'
     },
     canActivate: [UserRouteAccessService]
   }
];

export const clientPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ClientDeletePopupComponent,
    resolve: {
      client: ClientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customersApp.client.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
