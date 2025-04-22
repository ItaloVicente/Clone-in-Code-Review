	public static Test suite() {
		TestSuite suite = new TestSuite(
				"org.eclipse.ua.tests.forms.AllLayoutTests");
		suite.addTestSuite(TestTableWrapLayout.class);
		suite.addTestSuite(TestColumnWrapLayout.class);
		return suite;
	}

