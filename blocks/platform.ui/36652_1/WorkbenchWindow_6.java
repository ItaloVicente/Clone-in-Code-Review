	private boolean shouldCloseWorkbench(Workbench workbench) {


		if (workbench.isStarting())
			return false;

		if (workbench.isClosing())
			return false;

		int count = workbench.getWorkbenchWindowCount();

		if (count > 1)
			return false;

		return workbench.getWorkbenchConfigurer().getExitOnLastWindowClose();
	}

