	@Test(expected = IllegalArgumentException.class)
	public void testGetKeyFormat_failsForInvalidValue() throws Exception {
		);

		new GpgConfig(c).getKeyFormat();
		fail("Call should not have succeeded!");
