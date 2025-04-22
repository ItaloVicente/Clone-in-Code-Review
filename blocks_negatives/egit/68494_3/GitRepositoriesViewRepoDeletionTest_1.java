	@SuppressWarnings("restriction")
	private void waitForDecorations() throws InterruptedException {
		TestUtil.joinJobs(
				org.eclipse.ui.internal.decorators.DecoratorManager.FAMILY_DECORATE);
	}

