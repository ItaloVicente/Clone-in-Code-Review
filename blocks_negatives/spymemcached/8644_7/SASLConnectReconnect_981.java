    /**
     *
     * The intent of this test is to verify that if MemcachedClient object is
     * set up for SASL Auth the operations are only sent after SASL auth has
     * been completed, even in a reconnect case.
     *
     * At the moment, I use external start/restart of the memcached and
     * external verification that the behavior was correct.
     *
     * Example arguments for running this test:
     * username password 127.0.0.1:11211 10000
     *
     * The initial run does it's thing, then pauses for 30 seconds, while
     * I bounce the server.  Then it runs the second pass.
     *
     * @param args the command line arguments
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
	if (args.length != 4) {
	    System.err.println("Usage example:\nQuickAuthLoad user password localhost:11212 10000");
	    System.exit(1);
	}
	SASLConnectReconnect m = new SASLConnectReconnect(args[0], args[1], args[2]);

	Logger.getLogger("net.spy.memcached").setLevel(Level.FINEST);

	Logger topLogger = java.util.logging.Logger.getLogger("");

	Handler consoleHandler = null;
	for (Handler handler : topLogger.getHandlers()) {
	    if (handler instanceof ConsoleHandler) {
		consoleHandler = handler;
		break;
	    }
	}

	if (consoleHandler == null) {
	    consoleHandler = new ConsoleHandler();
	    topLogger.addHandler(consoleHandler);
	}
	consoleHandler.setLevel(java.util.logging.Level.FINEST);

	m.verifySetAndGet();
	System.err.println("Pass one done.");
	Thread.sleep(60000);
	m.verifySetAndGet2(Integer.parseInt(args[3]));
	System.err.println("Pass two done.");

