	@SuppressWarnings("restriction")
	public static void waitForDecorations() throws InterruptedException {
		TestUtil.joinJobs(
				org.eclipse.ui.internal.decorators.DecoratorManager.FAMILY_DECORATE);
	}

