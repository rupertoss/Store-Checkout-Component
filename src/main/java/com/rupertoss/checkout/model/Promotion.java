package com.rupertoss.checkout.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Promotion")
public class Promotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@NotBlank
	@Column(name = "Code")
	private String code;
	
	@Column(name = "Description")
	private String description;
	
	@NotNull
	@Column(name = "Discount")
	private double discount;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar validTill;

	public Promotion() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Calendar getValidTill() {
		return validTill;
	}

	public void setValidTill(Calendar validTill) {
		this.validTill = validTill;
	}
}
