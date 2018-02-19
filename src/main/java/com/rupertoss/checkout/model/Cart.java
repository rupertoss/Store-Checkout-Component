package com.rupertoss.checkout.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
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
	private Long id;
	
	@NotNull
	@ElementCollection
	@CollectionTable(name="Cart_Items", joinColumns = @JoinColumn(name = "ID"))
	@MapKeyColumn(name = "ItemID")
	@Column(name = "ItemQuantity")
	private Map<Integer, Integer> items = new HashMap<Integer, Integer>();
	
	@Column(name = "Value")
	private BigDecimal value;
	
	public Cart() {
	}
	
	public Cart(Long id, Map<Integer, Integer> items, BigDecimal value) {
		this.id = id;
		this.items = items;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public Map<Integer, Integer> getItems() {
		return items;
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", items=" + items + ", value=" + value + "]";
	}
}
