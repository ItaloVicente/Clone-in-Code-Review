	@BeforeClass
	public static void setup() throws Exception {
		r = new SampleTestRepository();
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		r.shutDown();
	}

