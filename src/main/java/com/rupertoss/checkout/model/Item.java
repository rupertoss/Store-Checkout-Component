package com.rupertoss.checkout.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Item")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@NotNull
	@Column(name = "Price")
	private BigDecimal price;
	
	@NotBlank
	@Lob
	@Column(name = "Description")
	private String description;
	
	@NotNull
	@Column(name = "SpecialQuantity")
	private int specialQuantity;
	
	@NotNull
	@Column(name = "SpecialPrice")
	private BigDecimal specialPrice;
	
	public Item() {
	}

	public Item(Integer id, String description, BigDecimal price, BigDecimal specialPrice, int specialQuantity) {
		this.id = id;
		this.price = price;
		this.description = description;
		this.specialQuantity = specialQuantity;
		this.specialPrice = specialPrice;
	}

	public Integer getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public int getSpecialQuantity() {
		return specialQuantity;
	}

	public BigDecimal getSpecialPrice() {
		return specialPrice;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", price=" + price + ", description=" + description + ", specialQuantity="
				+ specialQuantity + ", specialPrice=" + specialPrice + "]";
	}
}
