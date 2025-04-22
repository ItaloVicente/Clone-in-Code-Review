    public void testSetEnabledExpressionActivity() {
    	try {
    		TestSourceProvider testSourceProvider = new TestSourceProvider();
    		IEvaluationService evalService = PlatformUI
    				.getWorkbench().getService(IEvaluationService.class);
    		evalService.addSourceProvider(testSourceProvider);
    		testSourceProvider.fireSourceChanged();
