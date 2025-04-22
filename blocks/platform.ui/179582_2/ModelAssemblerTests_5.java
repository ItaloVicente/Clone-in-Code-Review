		boolean completed = countDownLatch.await(COUNTDOWN_TIMEOUT, TimeUnit.MILLISECONDS);
		assertTrue("Timeout - no event received", completed);

		assertEquals(1, logMessages.size());
		assertEquals(
				"Unable to create processor org.eclipse.e4.ui.tests.workbench.SimplePreProcessor_NotFound from org.eclipse.e4.ui.tests",
				logMessages.poll());

