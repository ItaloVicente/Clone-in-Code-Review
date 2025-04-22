		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(9
		assertEquals(2

