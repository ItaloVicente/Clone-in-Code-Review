	@Test
	public void testStaleListener() {
		AtomicInteger nrEvents = new AtomicInteger();

		delayed.addStaleListener(e -> {
			nrEvents.incrementAndGet();
			assertTrue(delayed.isStale());
		});

		target.setValue(newValue);

		assertTrue(delayed.isStale());
		assertEquals(1, nrEvents.get());

		delayed.getValue();
		assertFalse(delayed.isStale());

		assertEquals(1, nrEvents.get());
	}

