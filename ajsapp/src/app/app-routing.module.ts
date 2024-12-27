import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AuthGuard } from './auth.guard';
import { LoggedInGuard } from './logout.LoggedInGuard';
import { TokenComponent } from './token/token.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent,canActivate:[LoggedInGuard]},
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'token', component: TokenComponent},
  { path: 'home', component:  HomeComponent,canActivate: [AuthGuard]},//
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
