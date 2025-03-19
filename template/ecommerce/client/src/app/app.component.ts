import { Component, OnInit, inject } from '@angular/core';
import {lastValueFrom, Observable} from 'rxjs';
import {Router} from '@angular/router';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  // NOTE: you are free to modify this component

  private router = inject(Router)
  private cartStore = inject(CartStore)

  itemCount$!: Observable<number>;
  noItems: boolean = true;

  ngOnInit(): void {
    this.itemCount$ = this.cartStore.getItemCount;
    this.itemCount$.subscribe(
      value => {
        if(value > 0){
          this.noItems = false;
        }
      }
    )
  }

  checkout(): void {
    this.router.navigate([ '/checkout' ])
  }
}
