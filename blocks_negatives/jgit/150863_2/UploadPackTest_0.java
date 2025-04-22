	@Test
	public void testV2FetchSidebandAllIfNotAllowed() throws Exception {
		checkV2FetchWhenNotAllowed(
			"sideband-all\n",
			"unexpected sideband-all");
	}

