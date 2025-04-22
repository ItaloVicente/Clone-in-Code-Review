	@Test
	public void test_log_setThrottle() {
		LogThrottle throttle = new LogThrottle(QUEUE_SIZE, 3);
		
		for (int i = 0; i < 5; i++) {
			throttle.log(LogLevel.ERROR.ordinal(), "foo", null);
		}
		
		verify(logListener, atMost(3)).logged(logEntryMatcher(LogLevel.ERROR, "foo"));
		verify(logListener, atMost(1))
		.logged(logEntryMatcher(LogLevel.WARN, "The previous message has been throttled.*"));
		
		throttle.setThrottle(2)

		for (int i = 0; i < 5; i++) {
			throttle.log(LogLevel.ERROR.ordinal(), "bar", null);
		}
		
		verify(logListener, atMost(2)).logged(logEntryMatcher(LogLevel.ERROR, "bar"));

	}

