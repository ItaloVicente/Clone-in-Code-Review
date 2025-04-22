	client.addOp("x", op);
	System.err.println("Operation attempted:");
	System.err.println(op);
	System.err.println("Trying to get:");
	try {
		client.get("x");
		String retValString = new String();
		System.err.println(retValString);
	}
	catch (net.spy.memcached.OperationTimeoutException ex) {
		System.err.println("Timed out successfully: " + ex.getMessage());
	}
