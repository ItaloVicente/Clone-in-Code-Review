	@After
	public void resetDecoratorEnablement() throws CoreException {
		IDecoratorManager manager = PlatformUI.getWorkbench().getDecoratorManager();
		manager.setEnabled("org.eclipse.ui.tests.navigator.bug417255Decorator", false);
	}

