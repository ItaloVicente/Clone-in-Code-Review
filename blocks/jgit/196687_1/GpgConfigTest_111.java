	@Test
	void testGetKeyFormat_failsForInvalidValue() throws Exception {
		assertThrows(IllegalArgumentException.class
			);

			new GpgConfig(c).getKeyFormat();
			fail("Call should not have succeeded!");
		});
