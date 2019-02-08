package com.revature.Models;

import java.io.Serializable;

public class Account implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4194688300875098343L;
	private String type;
	private double amount;
	private Integer accId;
	
	public Account() {
		super();
	}

	public Account(String type, double amount, Integer accId) {
		super();
		this.type = type;
		this.amount = amount;
		this.accId = accId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Integer getAccId() {
		return accId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accId == null) ? 0 : accId.hashCode());
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accId == null) {
			if (other.accId != null)
				return false;
		} else if (!accId.equals(other.accId))
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [type=" + type + ", amount=" + amount + ", accId=" + accId + "]";
	}

	
	
}
