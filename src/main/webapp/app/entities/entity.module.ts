import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.StartupPocProjectModule),
      },
      {
        path: 'work-unit',
        loadChildren: () => import('./work-unit/work-unit.module').then(m => m.StartupPocWorkUnitModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class StartupPocEntityModule {}
