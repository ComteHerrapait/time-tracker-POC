import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkUnit } from 'app/shared/model/work-unit.model';

@Component({
  selector: 'jhi-work-unit-detail',
  templateUrl: './work-unit-detail.component.html',
})
export class WorkUnitDetailComponent implements OnInit {
  workUnit: IWorkUnit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workUnit }) => (this.workUnit = workUnit));
  }

  previousState(): void {
    window.history.back();
  }
}
