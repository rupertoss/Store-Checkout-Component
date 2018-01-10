package com.rupertoss.checkout.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Item")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@NotBlank
	@Column(name = "Price")
	private double price;
	
	@NotBlank
	@Lob
	@Column(name = "Description")
	private String description;
	
	@NotBlank
	@Column(name = "SpecialQuantity")
	private int specialQuantity;
	
	@NotBlank
	@Column(name = "SpecialPrice")
	private double specialPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSpecialQuantity() {
		return specialQuantity;
	}

	public void setSpecialQuantity(int specialQuantity) {
		this.specialQuantity = specialQuantity;
	}

	public double getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(double specialPrice) {
		this.specialPrice = specialPrice;
	}
	
	
	
}
