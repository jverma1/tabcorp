package com.tabcorp.application.bet.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tabcorp.application.bet.model.AggregateResults;
import com.tabcorp.application.bet.model.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

	@Query(value = "SELECT new com.tabcorp.application.bet.model.AggregateResults(SUM(investment) as totalInvestment, betType ) FROM Bet Group by betType")
	List<AggregateResults> getTotalInvestmentPerBetType();

	@Query(value = "SELECT new com.tabcorp.application.bet.model.AggregateResults(SUM(investment) as totalInvestment, customerID ) FROM Bet Group by customerID")
	List<AggregateResults> getTotalInvestmentPerCustomerID();

	@Query(value = "SELECT new com.tabcorp.application.bet.model.AggregateResults(Count(*) as betCount, betType ) FROM Bet Group by betType")
	List<AggregateResults> getTotalBetsSoldPerBetType();

	@Query(value = "SELECT b FROM Bet b where b.dateTime >= :fromDate AND b.dateTime < :toDate")
	List<Bet> getTotalNumberOfBetsSoldPerHour(LocalDateTime fromDate, LocalDateTime toDate);

}
