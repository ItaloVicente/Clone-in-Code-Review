		if ((filterTestClassName != null) || (filterTestName != null)) {
			if (test instanceof TestSuite) {
				addFilteredTestSuite((TestSuite)test);
				return;
			} else if (test instanceof TestCase) {
				addFilteredTestCase((TestCase)test);
				return;
			}
		}
		super.addTest(test);
	}

	private void addFilteredTestSuite(TestSuite testSuite) {
