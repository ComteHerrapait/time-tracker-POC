import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StartupPocTestModule } from '../../../test.module';
import { WorkUnitComponent } from 'app/entities/work-unit/work-unit.component';
import { WorkUnitService } from 'app/entities/work-unit/work-unit.service';
import { WorkUnit } from 'app/shared/model/work-unit.model';

describe('Component Tests', () => {
  describe('WorkUnit Management Component', () => {
    let comp: WorkUnitComponent;
    let fixture: ComponentFixture<WorkUnitComponent>;
    let service: WorkUnitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StartupPocTestModule],
        declarations: [WorkUnitComponent],
      })
        .overrideTemplate(WorkUnitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkUnitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkUnitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkUnit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workUnits && comp.workUnits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
