package com.cafe.manager.exceptions;

public class OpenedOrderExistingException extends RuntimeException {

	public OpenedOrderExistingException() {
		super("The table already has an order with 'open' status");
	}
}
