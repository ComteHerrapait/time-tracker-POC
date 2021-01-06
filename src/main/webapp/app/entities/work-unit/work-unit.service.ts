import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkUnit } from 'app/shared/model/work-unit.model';

type EntityResponseType = HttpResponse<IWorkUnit>;
type EntityArrayResponseType = HttpResponse<IWorkUnit[]>;

@Injectable({ providedIn: 'root' })
export class WorkUnitService {
  public resourceUrl = SERVER_API_URL + 'api/work-units';

  constructor(protected http: HttpClient) {}

  create(workUnit: IWorkUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workUnit);
    return this.http
      .post<IWorkUnit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(workUnit: IWorkUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workUnit);
    return this.http
      .put<IWorkUnit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorkUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorkUnit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(workUnit: IWorkUnit): IWorkUnit {
    const copy: IWorkUnit = Object.assign({}, workUnit, {
      date: workUnit.date && workUnit.date.isValid() ? workUnit.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((workUnit: IWorkUnit) => {
        workUnit.date = workUnit.date ? moment(workUnit.date) : undefined;
      });
    }
    return res;
  }
}
