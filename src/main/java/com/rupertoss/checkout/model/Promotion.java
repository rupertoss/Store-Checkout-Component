package com.rupertoss.checkout.model;

import java.math.BigDecimal;
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
	private BigDecimal discount;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar validTill;

	public Promotion() {
	}

	public Promotion(int id, String code, String description, BigDecimal discount, Calendar validTill) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.discount = discount;
		this.validTill = validTill;
	}

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public Calendar getValidTill() {
		return validTill;
	}

	@Override
	public String toString() {
		return "Promotion [id=" + id + ", code=" + code + ", description=" + description + ", discount=" + discount
				+ ", validTill=" + validTill + "]";
	}
}
