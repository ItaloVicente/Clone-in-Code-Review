	@Override
	protected void tearDown() throws Exception {

		/*
		 * ensure the workbench state gets saved when running with the Automated Testing Framework
				 */
		StackTraceElement[] elements=  new Throwable().getStackTrace();
		for (StackTraceElement element : elements) {
			if (element.getClassName().equals("org.eclipse.test.EclipseTestRunner")) {
				PlatformUI.getWorkbench().close();
				break;
			}
		}
	}

