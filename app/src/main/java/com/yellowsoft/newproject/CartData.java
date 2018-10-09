package com.yellowsoft.newproject;

import java.io.Serializable;

public class CartData  implements Serializable{
	String title,price,id,quantity;
	public CartData(String title,String price,String id,String quantity){
		this.title=title;
		this.price = price;
		this.id=id;
		this.quantity = quantity;


	}
}
