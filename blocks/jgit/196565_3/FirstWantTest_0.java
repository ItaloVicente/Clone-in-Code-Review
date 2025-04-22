
	@Test
	public void testFirstWantValidSessionID() throws PackProtocolException {
		FirstWant r = FirstWant
				.fromLine(makeFirstWantLine("session-id=client.session.id"));
		assertEquals(r.getCapabilities().size()
		assertEquals("client.session.id"
	}
