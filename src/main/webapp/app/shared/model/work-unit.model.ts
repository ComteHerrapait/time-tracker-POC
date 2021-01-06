import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IProject } from 'app/shared/model/project.model';

export interface IWorkUnit {
  id?: number;
  duration?: number;
  date?: Moment;
  description?: string;
  user?: IUser;
  project?: IProject;
}

export class WorkUnit implements IWorkUnit {
  constructor(
    public id?: number,
    public duration?: number,
    public date?: Moment,
    public description?: string,
    public user?: IUser,
    public project?: IProject
  ) {}
}
