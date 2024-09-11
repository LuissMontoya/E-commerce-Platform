import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientComponent } from './components/client/client.component';
import { ListOrderComponent } from './components/list-order/list-order.component';
import { OrderComponent } from './components/order/order.component';
import { ListProductComponent } from './components/list-product/list-product.component';
import { ProductComponent } from './components/product/product.component';

const routes: Routes = [
  { path: 'app-client-form', component: ClientComponent },
  { path: 'app-list-order', component: ListOrderComponent },
  { path: 'app-list-product', component: ListProductComponent },
  //{ path: '', redirectTo: '/app-client-form', pathMatch: 'full' }, 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}