	private void setupApplication(ApplicationType type) {
		switch (type) {
		case SIMPLE:
			setupSimpleApplication();
			break;
		case WORKBENCH:
			setupWorkbenchApplication();
			break;
		}
	}
