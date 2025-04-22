		assertEquals(batchesRefUpdates() ?3 : 4
		if (useReftable) {
			assertReflogEquals(
					reflog(A
					getLastReflog("refs/heads/master"));
		} else {
			assertNull(getLastReflog("refs/heads/master"));
		}
