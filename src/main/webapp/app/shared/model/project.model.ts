import { Moment } from 'moment';

export interface IProject {
  id?: number;
  name?: string;
  deadline?: Moment;
}

export class Project implements IProject {
  constructor(public id?: number, public name?: string, public deadline?: Moment) {}
}
