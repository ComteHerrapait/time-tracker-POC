import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkUnit } from 'app/shared/model/work-unit.model';
import { WorkUnitService } from './work-unit.service';
import { WorkUnitDeleteDialogComponent } from './work-unit-delete-dialog.component';

@Component({
  selector: 'jhi-work-unit',
  templateUrl: './work-unit.component.html',
})
export class WorkUnitComponent implements OnInit, OnDestroy {
  workUnits?: IWorkUnit[];
  eventSubscriber?: Subscription;

  constructor(protected workUnitService: WorkUnitService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.workUnitService.query().subscribe((res: HttpResponse<IWorkUnit[]>) => (this.workUnits = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkUnits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkUnit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkUnits(): void {
    this.eventSubscriber = this.eventManager.subscribe('workUnitListModification', () => this.loadAll());
  }

  delete(workUnit: IWorkUnit): void {
    const modalRef = this.modalService.open(WorkUnitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workUnit = workUnit;
  }
}
