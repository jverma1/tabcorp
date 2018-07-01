package com.tabcorp.application.bet.controller;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.tabcorp.application.bet.dao.BetRepository;
import com.tabcorp.application.bet.model.AggregateResults;
import com.tabcorp.application.bet.model.Bet;
import com.tabcorp.application.bet.model.BetType;

@RunWith(MockitoJUnitRunner.class)
public class BettingControllerTest {

	@Mock
	private BetRepository betRepository;

	@InjectMocks
	BettingController betController = new BettingController();

	private Bet bet1;
	private Bet bet2;

	@Before
	public void before() {
		bet1 = new Bet(LocalDateTime.now(), BetType.WIN, 10101, 101, new BigDecimal(200));
		bet2 = new Bet(LocalDateTime.now(), BetType.PLACE, 10102, 102, new BigDecimal(100));
	}

	@Test
	public void createTest() {
		List<Bet> betList = new ArrayList<>();

		betList.add(bet1);
		betList.add(bet2);

		when(betRepository.save(Mockito.any(Bet.class))).thenReturn(bet1, bet2);
		ResponseEntity<List<Bet>> responseEntity = betController.create(betList);
		List<Bet> resultList = responseEntity.getBody();
		Assert.assertEquals(2, resultList.size());
		Assert.assertEquals(BetType.WIN, resultList.get(0).getBetType());
		Assert.assertEquals(BetType.PLACE, resultList.get(1).getBetType());

	}

	@Test
	public void getInvestmentByBetTypeTest() {
		AggregateResults results1 = new AggregateResults(new BigDecimal(100), BetType.WIN);
		AggregateResults results2 = new AggregateResults(new BigDecimal(200), BetType.TRIFECTA);

		List<AggregateResults> aggregateResults = new ArrayList<>();
		aggregateResults.add(results1);
		aggregateResults.add(results2);

		when(betRepository.getTotalInvestmentPerBetType()).thenReturn(aggregateResults);
		ResponseEntity<List<AggregateResults>> responseEntity = betController.getInvestmentByBetType();
		List<AggregateResults> resultList = responseEntity.getBody();
		Assert.assertEquals(2, resultList.size());
		Assert.assertEquals(BetType.WIN, resultList.get(0).getBetType());
		Assert.assertEquals(BetType.TRIFECTA, resultList.get(1).getBetType());
		Assert.assertEquals(new BigDecimal(100), resultList.get(0).getTotalInvestment());
		Assert.assertEquals(new BigDecimal(200), resultList.get(1).getTotalInvestment());
	}

	@Test
	public void getInvestmentByCustomerIdTest() {
		AggregateResults results1 = new AggregateResults(new BigDecimal(250), 101);
		AggregateResults results2 = new AggregateResults(new BigDecimal(350), 102);

		List<AggregateResults> aggregateResults = new ArrayList<>();
		aggregateResults.add(results1);
		aggregateResults.add(results2);

		when(betRepository.getTotalInvestmentPerCustomerID()).thenReturn(aggregateResults);
		ResponseEntity<List<AggregateResults>> responseEntity = betController.getInvestmentByCustomerId();
		List<AggregateResults> resultList = responseEntity.getBody();
		Assert.assertEquals(2, resultList.size());
		Assert.assertEquals(results1.getcustomerID(), resultList.get(0).getcustomerID());
		Assert.assertEquals(results2.getcustomerID(), resultList.get(1).getcustomerID());
		Assert.assertEquals(new BigDecimal(250), resultList.get(0).getTotalInvestment());
		Assert.assertEquals(new BigDecimal(350), resultList.get(1).getTotalInvestment());
	}

	@Test
	public void getTotalBetsSoldPerBetTypeTest() {
		AggregateResults results1 = new AggregateResults(2L, BetType.WIN);
		AggregateResults results2 = new AggregateResults(3L, BetType.PLACE);

		List<AggregateResults> aggregateResults = new ArrayList<>();
		aggregateResults.add(results1);
		aggregateResults.add(results2);

		when(betRepository.getTotalBetsSoldPerBetType()).thenReturn(aggregateResults);
		ResponseEntity<List<AggregateResults>> responseEntity = betController.getTotalBetsSoldPerBetType();
		List<AggregateResults> resultList = responseEntity.getBody();
		Assert.assertEquals(2, resultList.size());
		Assert.assertEquals(results1.getBetCount(), resultList.get(0).getBetCount());
		Assert.assertEquals(results2.getBetCount(), resultList.get(1).getBetCount());
		Assert.assertEquals(BetType.WIN, resultList.get(0).getBetType());
		Assert.assertEquals(BetType.PLACE, resultList.get(1).getBetType());
	}

	@Test
	public void getTotalNumberOfBetsSoldPerHourTest() {
		LocalDateTime localDateTime1 = LocalDateTime.of(2018, Month.JUNE, 28, 14, 25);
		LocalDateTime localDateTime2 = LocalDateTime.of(2018, Month.JUNE, 28, 14, 55);
		LocalDateTime localDateTime3 = LocalDateTime.of(2018, Month.JUNE, 28, 15, 25);

		Bet bet1 = new Bet(localDateTime1, BetType.WIN, 10101, 101, new BigDecimal(200.50));
		Bet bet2 = new Bet(localDateTime2, BetType.PLACE, 10102, 102, new BigDecimal(500));
		Bet bet3 = new Bet(localDateTime3, BetType.TRIFECTA, 10103, 103, new BigDecimal(300.50));

		List<Bet> betList = new ArrayList<>();

		betList.add(bet1);
		betList.add(bet2);
		betList.add(bet3);

		when(betRepository.getTotalNumberOfBetsSoldPerHour(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class))).thenReturn(betList);

		ResponseEntity<Map<String, Integer>> responseEntity = betController
				.getTotalNumberOfBetsSoldPerHour(LocalDate.now(), LocalDate.now());
		Map<String, Integer> resultMap = responseEntity.getBody();
		Assert.assertEquals(2, resultMap.size());
		Assert.assertEquals(2, resultMap.get("2018-06-28-14").intValue());
		Assert.assertEquals(1, resultMap.get("2018-06-28-15").intValue());
	}

}
