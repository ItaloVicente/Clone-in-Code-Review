		boolean completed = countDownLatch.await(COUNTDOWN_TIMEOUT, TimeUnit.MILLISECONDS);
		assertTrue("Timeout - no event received", completed);

		assertEquals(1, logMessages.size());
		assertEquals("Unable to create processor null from org.eclipse.e4.ui.tests", logMessages.poll());

