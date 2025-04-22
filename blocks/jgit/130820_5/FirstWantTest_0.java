
	private String makeFirstWantLine(String capability) {
		return String.format("want b9d4d1eb2f93058814480eae9e1b67550f46ee38 %s"
	}

	@Test
	public void testFirstWantNoWhitespace() {
		try {
			FirstWant.fromLine(
					"want b9d4d1eb2f93058814480eae9e1b67550f400000capability");
			fail("Accepting first want line without SP between oid and first capability");
		} catch (PackProtocolException e) {
		}
	}

	@Test
	public void testFirstWantOnlyWhitespace() throws PackProtocolException {
		FirstWant r = FirstWant
				.fromLine("want b9d4d1eb2f93058814480eae9e1b67550f46ee38 ");
		assertEquals("want b9d4d1eb2f93058814480eae9e1b67550f46ee38"
				r.getLine());
	}

	@Test
	public void testFirstWantValidCapabilityNames()
			throws PackProtocolException {
		List<String> validNames = Arrays.asList(
				"c"
				"-"
				"_"

		for (String capability: validNames) {
			FirstWant r = FirstWant.fromLine(makeFirstWantLine(capability));
			assertEquals(r.getCapabilities().size()
			assertTrue(r.getCapabilities().contains(capability));
		}
	}

	@Test
	public void testFirstWantInvalidCapabilityNames() {
		List<String> invalidNames = Arrays.asList("\0cap"
				new String(new byte[] { 0x1b

		for (String capability : invalidNames) {
			try {
				FirstWant.fromLine(makeFirstWantLine(capability));
				fail("Accepted invalid capability " + capability);
			} catch (PackProtocolException e) {
			}
		}
	}
