	public static TestSuite suite() {
		TestSuite suite = new TestSuite("org.eclipse.ui.tests.navigator.WorkingSetTest");
		suite.addTest(new WorkingSetTest("testEmptyWindowWorkingSet"));
		suite.addTest(new WorkingSetTest("testMissingProjectsInWorkingSet"));
		suite.addTest(new WorkingSetTest("testTopLevelWorkingSet"));
		suite.addTest(new WorkingSetTest("testTopLevelChange"));
		suite.addTest(new WorkingSetTest("testMultipleWorkingSets"));
		suite.addTest(new WorkingSetTest("testWorkingSetFilter"));
		suite.addTest(new WorkingSetTest("testDeletedAndRecreated"));
		return suite;
	}
