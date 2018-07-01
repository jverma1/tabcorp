package com.tabcorp.application.bet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Bet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@JsonProperty("DateTime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime dateTime;
	@JsonProperty("BetType")
	private BetType betType;
	@JsonProperty("PropNumber")
	private int propNumber;
	@JsonProperty("CustomerID")
	private int customerID;
	@JsonProperty("Investment")
	private BigDecimal investment;

	public Bet(LocalDateTime dateTime, BetType betType, int propNumber, int customerID, BigDecimal investment) {
		this.dateTime = dateTime;
		this.betType = betType;
		this.propNumber = propNumber;
		this.customerID = customerID;
		this.investment = investment;
	}

	public Bet() {
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public BetType getBetType() {
		return betType;
	}

	public void setBetType(BetType betType) {
		this.betType = betType;
	}

	public int getPropNumber() {
		return propNumber;
	}

	public void setPropNumber(int propNumber) {
		this.propNumber = propNumber;
	}

	public int getcustomerID() {
		return customerID;
	}

	public void setcustomerID(int customerID) {
		this.customerID = customerID;
	}

	public BigDecimal getInvestment() {
		return investment;
	}

	public void setInvestment(BigDecimal investment) {
		this.investment = investment;
	}

	@Override
	public String toString() {
		return "Bet [dateTime=" + dateTime + ", betType=" + betType + ", propNumber=" + propNumber + ", customerID="
				+ customerID + ", investment=" + investment + "]";
	}
}
