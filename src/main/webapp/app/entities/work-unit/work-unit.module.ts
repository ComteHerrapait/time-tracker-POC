import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StartupPocSharedModule } from 'app/shared/shared.module';
import { WorkUnitComponent } from './work-unit.component';
import { WorkUnitDetailComponent } from './work-unit-detail.component';
import { WorkUnitUpdateComponent } from './work-unit-update.component';
import { WorkUnitDeleteDialogComponent } from './work-unit-delete-dialog.component';
import { workUnitRoute } from './work-unit.route';

@NgModule({
  imports: [StartupPocSharedModule, RouterModule.forChild(workUnitRoute)],
  declarations: [WorkUnitComponent, WorkUnitDetailComponent, WorkUnitUpdateComponent, WorkUnitDeleteDialogComponent],
  entryComponents: [WorkUnitDeleteDialogComponent],
})
export class StartupPocWorkUnitModule {}
