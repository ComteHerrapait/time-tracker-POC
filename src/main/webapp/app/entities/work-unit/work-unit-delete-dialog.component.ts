import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkUnit } from 'app/shared/model/work-unit.model';
import { WorkUnitService } from './work-unit.service';

@Component({
  templateUrl: './work-unit-delete-dialog.component.html',
})
export class WorkUnitDeleteDialogComponent {
  workUnit?: IWorkUnit;

  constructor(protected workUnitService: WorkUnitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workUnitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workUnitListModification');
      this.activeModal.close();
    });
  }
}
