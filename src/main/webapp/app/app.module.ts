import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { StartupPocSharedModule } from 'app/shared/shared.module';
import { StartupPocCoreModule } from 'app/core/core.module';
import { StartupPocAppRoutingModule } from './app-routing.module';
import { StartupPocHomeModule } from './home/home.module';
import { StartupPocEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    StartupPocSharedModule,
    StartupPocCoreModule,
    StartupPocHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    StartupPocEntityModule,
    StartupPocAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class StartupPocAppModule {}
