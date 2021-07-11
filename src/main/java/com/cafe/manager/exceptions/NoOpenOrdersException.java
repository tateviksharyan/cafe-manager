package com.cafe.manager.exceptions;

public class NoOpenOrdersException extends RuntimeException {

	public NoOpenOrdersException() {
		super("Table has no orders with 'open' status");
	}
}
