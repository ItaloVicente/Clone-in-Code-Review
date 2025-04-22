		r = new SampleTestRepository(NUMBER_RANDOM_COMMITS);
	}

	@AfterClass
	public static void tearDown() throws IOException {
		r.shutDown();
