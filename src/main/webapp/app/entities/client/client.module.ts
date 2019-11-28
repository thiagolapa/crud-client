import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomersSharedModule } from 'app/shared/shared.module';
import { ClientComponent } from './client.component';
import { ClientDetailComponent } from './client-detail.component';
import { ClientSimulationComponent } from './client-simulation.component';
import { ClientUpdateComponent } from './client-update.component';
import { ClientDeletePopupComponent, ClientDeleteDialogComponent } from './client-delete-dialog.component';
import { clientRoute, clientPopupRoute } from './client.route';

const ENTITY_STATES = [...clientRoute, ...clientPopupRoute];

@NgModule({
  imports: [CustomersSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ClientComponent, ClientDetailComponent, ClientSimulationComponent, ClientUpdateComponent, ClientDeleteDialogComponent, ClientDeletePopupComponent],
  entryComponents: [ClientDeleteDialogComponent]
})
export class CustomersClientModule {}
