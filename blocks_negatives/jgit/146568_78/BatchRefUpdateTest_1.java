		assertEquals(atomic ? 2 : 3, refsChangedEvents);
		assertRefs(
				"refs/heads/master", B,
				"refs/heads/branch", B);
		assertReflogEquals(
				reflog(A, B, ident, "a reflog"),
				getLastReflog("refs/heads/master"),
				true);
		assertReflogEquals(
				reflog(zeroId(), B, ident, "a reflog"),
				getLastReflog("refs/heads/branch"),
				true);
