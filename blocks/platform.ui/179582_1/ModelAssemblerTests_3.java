
		boolean completed = countDownLatch.await(500, TimeUnit.MILLISECONDS);
		assertTrue("Timeout - no event received", completed);

		assertEquals(1, logMessages.size());
		assertEquals("Could not resolve import for null", logMessages.poll());
