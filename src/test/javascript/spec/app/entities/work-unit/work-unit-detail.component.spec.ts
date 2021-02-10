import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StartupPocTestModule } from '../../../test.module';
import { WorkUnitDetailComponent } from 'app/entities/work-unit/work-unit-detail.component';
import { WorkUnit } from 'app/shared/model/work-unit.model';

describe('Component Tests', () => {
  describe('WorkUnit Management Detail Component', () => {
    let comp: WorkUnitDetailComponent;
    let fixture: ComponentFixture<WorkUnitDetailComponent>;
    const route = ({ data: of({ workUnit: new WorkUnit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StartupPocTestModule],
        declarations: [WorkUnitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkUnitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkUnitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workUnit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workUnit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
