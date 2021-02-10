import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkUnit, WorkUnit } from 'app/shared/model/work-unit.model';
import { WorkUnitService } from './work-unit.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

type SelectableEntity = IUser | IProject;

@Component({
  selector: 'jhi-work-unit-update',
  templateUrl: './work-unit-update.component.html',
})
export class WorkUnitUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  projects: IProject[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    duration: [null, [Validators.required]],
    date: [],
    description: [],
    user: [null, Validators.required],
    project: [],
  });

  constructor(
    protected workUnitService: WorkUnitService,
    protected userService: UserService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workUnit }) => {
      this.updateForm(workUnit);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));
    });
  }

  updateForm(workUnit: IWorkUnit): void {
    this.editForm.patchValue({
      id: workUnit.id,
      duration: workUnit.duration,
      date: workUnit.date,
      description: workUnit.description,
      user: workUnit.user,
      project: workUnit.project,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workUnit = this.createFromForm();
    if (workUnit.id !== undefined) {
      this.subscribeToSaveResponse(this.workUnitService.update(workUnit));
    } else {
      this.subscribeToSaveResponse(this.workUnitService.create(workUnit));
    }
  }

  private createFromForm(): IWorkUnit {
    return {
      ...new WorkUnit(),
      id: this.editForm.get(['id'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      date: this.editForm.get(['date'])!.value,
      description: this.editForm.get(['description'])!.value,
      user: this.editForm.get(['user'])!.value,
      project: this.editForm.get(['project'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkUnit>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
