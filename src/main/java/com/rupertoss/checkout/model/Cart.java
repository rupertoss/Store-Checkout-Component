package com.rupertoss.checkout.model;

import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public long id;
	
	@NotNull
	@ElementCollection
	@CollectionTable(name="Cart_Items", joinColumns = @JoinColumn(name = "ID"))
	@MapKeyColumn(name = "ItemID")
	@Column(name = "ItemQuantity")
	public Map<Integer, Integer> items = new TreeMap<Integer, Integer>();
	
	@Column(name = "CartValue")
	public double value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map<Integer, Integer> getItems() {
		return items;
	}

	public void setItems(Map<Integer, Integer> items) {
		this.items = items;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
