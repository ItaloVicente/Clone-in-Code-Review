
	@Test
	public void testFirstWantValidAgentName() throws PackProtocolException {
		FirstWant r = FirstWant.fromLine(makeFirstWantLine("agent=pack.age/Version"));
		assertEquals(r.getCapabilities().size()
		assertEquals("pack.age/Version"
	}
