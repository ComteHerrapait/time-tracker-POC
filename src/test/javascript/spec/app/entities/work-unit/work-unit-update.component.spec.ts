import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StartupPocTestModule } from '../../../test.module';
import { WorkUnitUpdateComponent } from 'app/entities/work-unit/work-unit-update.component';
import { WorkUnitService } from 'app/entities/work-unit/work-unit.service';
import { WorkUnit } from 'app/shared/model/work-unit.model';

describe('Component Tests', () => {
  describe('WorkUnit Management Update Component', () => {
    let comp: WorkUnitUpdateComponent;
    let fixture: ComponentFixture<WorkUnitUpdateComponent>;
    let service: WorkUnitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StartupPocTestModule],
        declarations: [WorkUnitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkUnitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkUnitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkUnitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkUnit(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkUnit();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
