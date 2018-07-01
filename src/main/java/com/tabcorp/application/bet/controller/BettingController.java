package com.tabcorp.application.bet.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tabcorp.application.bet.dao.BetRepository;
import com.tabcorp.application.bet.model.AggregateResults;
import com.tabcorp.application.bet.model.Bet;

@RestController
@RequestMapping(value = "/bets")
public class BettingController {

	private Logger logger = Logger.getLogger(BettingController.class.getName());
	@Autowired
	BetRepository betRepository;

	@PostMapping(value = "/create")
	public ResponseEntity<List<Bet>> create(@RequestBody List<Bet> bets) {
		logger.log(Level.INFO, "Creating bets");
		List<Bet> savedBets = new ArrayList<>();
		for (Bet bet : bets) {
			savedBets.add(betRepository.save(bet));
		}

		return new ResponseEntity<List<Bet>>(savedBets, HttpStatus.OK);
	}

	@GetMapping(value = "/getInvestmentPerBetType")
	public ResponseEntity<List<AggregateResults>> getInvestmentByBetType() {
		logger.log(Level.INFO, "Getting Investment per bet type ");
		return new ResponseEntity<List<AggregateResults>>(betRepository.getTotalInvestmentPerBetType(), HttpStatus.OK);

	}

	@GetMapping(value = "/getInvestmentPerCustomerID")
	public ResponseEntity<List<AggregateResults>> getInvestmentByCustomerId() {
		logger.log(Level.INFO, "Getting Investment per customerId ");
		return new ResponseEntity<List<AggregateResults>>(betRepository.getTotalInvestmentPerCustomerID(),
				HttpStatus.OK);

	}

	@GetMapping(value = "/getTotalBetsSoldPerBetType")
	public ResponseEntity<List<AggregateResults>> getTotalBetsSoldPerBetType() {
		logger.log(Level.INFO, "Getting total bets sold per bet type ");
		return new ResponseEntity<List<AggregateResults>>(betRepository.getTotalBetsSoldPerBetType(), HttpStatus.OK);

	}

	@GetMapping(value = "/getTotalNumberOfBetsSoldPerHour")
	public ResponseEntity<Map<String, Integer>> getTotalNumberOfBetsSoldPerHour(
			@RequestParam(value = "fromDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(value = "toDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		logger.log(Level.INFO, "Getting total number of bets sold per hour");
		List<Bet> betList = betRepository.getTotalNumberOfBetsSoldPerHour(fromDate.atStartOfDay(),
				toDate.atStartOfDay());
		Map<String, Integer> hourToBetsSoldMap = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
		for (Bet bet : betList) {
			String dateHour = formatter.format(bet.getDateTime());
			if (hourToBetsSoldMap.get(dateHour) != null) {
				hourToBetsSoldMap.put(dateHour, hourToBetsSoldMap.get(dateHour) + 1);
			} else {
				hourToBetsSoldMap.put(dateHour, new Integer(1));
			}

		}
		return new ResponseEntity<Map<String, Integer>>(hourToBetsSoldMap, HttpStatus.OK);

	}

}
