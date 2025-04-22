	}

	WorkbenchWindowConfigurer(WorkbenchWindow window) {
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.window = window;
		windowTitle = WorkbenchPlugin.getDefault().getProductName();
		if (windowTitle == null) {
			windowTitle = ""; //$NON-NLS-1$
		}
	}

	@Override
