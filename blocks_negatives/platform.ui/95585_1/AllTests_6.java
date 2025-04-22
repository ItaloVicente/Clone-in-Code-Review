	public static Test suite() {
		return new AllTests();
	}

	public AllTests() {
		addTestSuite(GeometryTest.class);
		addTestSuite(AbstractColumnLayoutTest.class);
		addTestSuite(GridDataFactoryTest.class);
		addTestSuite(GridLayoutFactoryTest.class);
		addTestSuite(TreeColumnLayoutTest.class);
	}
