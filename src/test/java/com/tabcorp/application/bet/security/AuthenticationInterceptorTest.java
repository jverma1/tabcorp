package com.tabcorp.application.bet.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationInterceptorTest {
	@Mock
	private Object handler;

	@InjectMocks
	AuthenticationInterceptor interceptor = new AuthenticationInterceptor();

	@Test
	public void preHandleTest() throws Exception {

		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/bets/create");
		MockHttpServletResponse response = new MockHttpServletResponse();

		assertFalse(interceptor.preHandle(request, response, handler));

		request.addHeader("Authorization", "abc123");
		assertTrue(interceptor.preHandle(request, response, handler));

	}

	@Test
	public void preHandleInvalidTokenTest() throws Exception {

		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/bets/create");
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.addHeader("Authorization", "abcd1234");
		assertFalse(interceptor.preHandle(request, response, handler));

	}

}
