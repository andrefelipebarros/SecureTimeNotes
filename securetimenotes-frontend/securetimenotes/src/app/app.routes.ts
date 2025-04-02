import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from '../app/components/login/login.component';
import { NgModule } from '@angular/core';

export const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path:'home', component: HomeComponent},

    // Authentication
    {path:'register', component: RegisterComponent},
    {path:'login', component: LoginComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }