	public static void addConformanceTest(ConformanceSuite suite) {
		suite.addTest(SWTMutableObservableValueContractTest.class, new Delegate<>(shell -> shell));
		suite.addTest(SWTMutableObservableValueContractTest.class, new Delegate<>(Menu::new));
		suite.addTest(SWTMutableObservableValueContractTest.class,
				new Delegate<>(shell -> new ToolTip(shell, SWT.BALLOON)));
		suite.addTest(SWTMutableObservableValueContractTest.class,
				new Delegate<>(shell -> new ToolBar(shell, SWT.HORIZONTAL)));
		suite.addTest(SWTMutableObservableValueContractTest.class, new Delegate<>(Shell::getHorizontalBar));

		suite.addTest(SWTObservableValueContractTest.class, new Delegate<>(shell -> shell));
		suite.addTest(SWTObservableValueContractTest.class, new Delegate<>(Menu::new));
		suite.addTest(SWTObservableValueContractTest.class, new Delegate<>(shell -> new ToolTip(shell, SWT.BALLOON)));
		suite.addTest(SWTObservableValueContractTest.class,
				new Delegate<>(shell -> new ToolBar(shell, SWT.HORIZONTAL)));
		suite.addTest(SWTObservableValueContractTest.class, new Delegate<>(Shell::getHorizontalBar));
