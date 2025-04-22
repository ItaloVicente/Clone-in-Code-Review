		assertEquals(atomic ? 2 : 3, refsChangedEvents);
		assertReflogEquals(
				reflog(A, B, ident, "custom log"),
				getLastReflog("refs/heads/master"),
				true);
		assertReflogEquals(
				reflog(zeroId(), B, ident, "a reflog: created"),
				getLastReflog("refs/heads/branch"),
				true);
