import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.CustomersAddressModule)
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.CustomersClientModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CustomersEntityModule {}
