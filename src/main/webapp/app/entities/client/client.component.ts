import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IClient } from 'app/shared/model/client.model';
import { ClientService } from './client.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiParseLinks, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-client',
  templateUrl: './client.component.html',
  styleUrls: ['client.scss']
})
export class ClientComponent implements OnInit, OnDestroy {
  clients: IClient[];
  eventSubscriber: Subscription;

  search = '';
  totalItems: any;
  selectedSex: string;
  page: any;
  itemsPerPage: any;
  predicate: any;
  reverse: any;
  previousPage: any;
  routeData: any;
  links: any;
  loading = false;

  constructor(
    protected clientService: ClientService,
    protected eventManager: JhiEventManager,
    protected router: Router,
    protected activatedRoute: ActivatedRoute,
    protected jhiAlertService: JhiAlertService,
    protected parseLinks: JhiParseLinks) {
      this.itemsPerPage = ITEMS_PER_PAGE;
      this.routeData = this.activatedRoute.data.subscribe(data => {
        this.page = data.pagingParams.page;
        this.previousPage = data.pagingParams.page;
        this.reverse = data.pagingParams.ascending;
        this.predicate = data.pagingParams.predicate;
      });
    }

  loadAll() {
    this.loading = true;
    this.clientService.query({
      page: this.page -1,
      size: this.itemsPerPage,
      q: this.search,
      risk: this.selectedSex ? this.selectedSex : '',
      sort: this.sort()
    })
    .subscribe(
      (res: HttpResponse<IClient[]>) => this.paginatePeople(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.selectedSex = null;
    this.loadAll();
    this.registerChangeInClients();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IClient) {
    return item.id;
  }

  registerChangeInClients() {
    this.eventSubscriber = this.eventManager.subscribe('clientListModification', () => this.loadAll());
  }

  doSearch() {
    this.page = 1;
    this.transition();
  }

  transition() {
    this.router.navigate(['/client'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  onSearchUpdate(term: string) {
    this.search = term;
    this.page = 0;
    this.loadAll();
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/client',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  protected paginatePeople(data: IClient[], headers: HttpHeaders) {
    this.loading = false;
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.clients = data;
  }

  changeTab(status: string) {
    this.selectedSex = status;
    this.loadAll();
  }

  private loadFilters(): void {
    this.page = 0;
    this.loadAll();
  }

  protected onError(errorMessage: string) {
    this.loading = false;
    this.jhiAlertService.error(errorMessage, null, null);
  }

}
