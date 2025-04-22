		boolean completed = countDownLatch.await(COUNTDOWN_TIMEOUT, TimeUnit.MILLISECONDS);
		assertTrue("Timeout - no event received", completed);

		assertEquals(1, logMessages.size());
		assertEquals("Could not find element with id org.eclipse.e4.ui.tests.modelassembler.app", logMessages.poll());

		assertEquals(0, logMessages.size());
