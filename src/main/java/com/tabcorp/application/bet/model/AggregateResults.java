package com.tabcorp.application.bet.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AggregateResults {

	private BetType betType;
	private BigDecimal totalInvestment;
	private Integer customerID;
	private Long betCount;

	public AggregateResults() {

	}

	public AggregateResults(BigDecimal totalInvestment, BetType betType) {
		this.betType = betType;
		this.totalInvestment = totalInvestment == null ? new BigDecimal(0) : totalInvestment;
	}

	public AggregateResults(BigDecimal totalInvestment, Integer customerID) {
		this.customerID = customerID;
		this.totalInvestment = totalInvestment == null ? new BigDecimal(0) : totalInvestment;
	}

	public AggregateResults(Long betCount, BetType betType) {
		this.betCount = betCount;
		this.betType = betType;
	}

	public BigDecimal getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(BigDecimal totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

	public BetType getBetType() {
		return betType;
	}

	public void setBetType(BetType betType) {
		this.betType = betType;
	}

	public Integer getcustomerID() {
		return customerID;
	}

	public void setcustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	public Long getBetCount() {
		return betCount;
	}

	public void setBetCount(Long betCount) {
		this.betCount = betCount;
	}

}
