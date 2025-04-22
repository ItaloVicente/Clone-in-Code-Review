		pool.shutdown();
		pool.awaitTermination(500, TimeUnit.MILLISECONDS);
		assertTrue("Threads did not complete, likely due to a deadlock.",
				pool.isTerminated());
		assertEquals(1, cache.getMissCount()[PackExt.BITMAP_INDEX.ordinal()]);
		assertEquals(1, cache.getMissCount()[PackExt.INDEX.ordinal()]);
