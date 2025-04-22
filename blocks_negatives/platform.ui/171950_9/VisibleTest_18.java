	public static void addConformanceTest(TestSuite suite) {
		suite.addTest(suite(new Delegate<>(shell -> shell)));
		suite.addTest(suite(new Delegate<>(Menu::new)));
		suite.addTest(suite(new Delegate<>(shell -> new ToolTip(shell, SWT.BALLOON))));
		suite.addTest(suite(new Delegate<>(shell -> new ToolBar(shell, SWT.HORIZONTAL))));
		suite.addTest(suite(new Delegate<>(Shell::getHorizontalBar)));
