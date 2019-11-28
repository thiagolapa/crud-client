import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IAddress } from 'app/shared/model/address.model';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from './client.service';

@Component({
  selector: 'jhi-client-simulation',
  templateUrl: './client-simulation.component.html',
  styleUrls: ['client.scss']
})
export class ClientSimulationComponent implements OnInit {
  client: IClient;
  loanAmount: number;
  duration: number;
  result: number;
  valueOfInstallments: number;
  loadingSimulation: boolean;

  constructor(protected activatedRoute: ActivatedRoute, protected clientService: ClientService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.client = client;
      this.loanAmount = null;
      this.duration = null;
      this.result = 0;
      this.valueOfInstallments = 0;
      this.loadingSimulation = false;
    });
  }

  previousState() {
    window.history.back();
  }

  trackId(index: number, item: IAddress) {
      return item.id;
  }

  simulation() {
    if ( this.client.risk === 'A' ) {
      this.result = this.loanAmount + this.loanAmount * 0.019;
      this.valueOfInstallments = this.result / this.duration;
    } else if ( this.client.risk === 'B' ) {
      this.result = this.loanAmount + this.loanAmount * 0.05;
      this.valueOfInstallments = this.result / this.duration;
    } else if ( this.client.risk === 'C' ) {
      this.result = this.loanAmount + this.loanAmount * 0.1;
      this.valueOfInstallments = this.result / this.duration;
    }
    this.loadingSimulation = true;
  }

  newSimulation() {
    this.result = null;
    this.valueOfInstallments = null;
    this.loadingSimulation = false;
    this.loanAmount = null;
    this.duration = null;
  }

}
