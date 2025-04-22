	@Test
	public void testPushStatusBug() {
		assertTrue(hasPushStatusBug(parseVersion("git/1.6.6")));
		assertTrue(hasPushStatusBug(parseVersion("git/1.6.6.1")));
		assertTrue(hasPushStatusBug(parseVersion("git/1.7.9")));

		assertFalse(hasPushStatusBug(parseVersion("git/1.7.8.6")));
		assertFalse(hasPushStatusBug(parseVersion("git/1.7.9.1")));
		assertFalse(hasPushStatusBug(parseVersion("git/1.7.9.2")));
		assertFalse(hasPushStatusBug(parseVersion("git/1.7.10")));
	}

