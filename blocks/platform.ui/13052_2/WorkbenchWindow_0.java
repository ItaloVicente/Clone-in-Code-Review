	private IPerspectiveDescriptor getPerspectiveOverride() {
		String perspId = null;
		String[] commandLineArgs = Platform.getCommandLineArgs();
		for (int i = 0; i < commandLineArgs.length - 1; i++) {
			if (commandLineArgs[i].equalsIgnoreCase("-perspective")) { //$NON-NLS-1$
				perspId = commandLineArgs[i + 1];
				break;
			}
		}
		if (perspId == null) {
			return null;
		}
		IPerspectiveDescriptor desc = getWorkbench().getPerspectiveRegistry()
				.findPerspectiveWithId(perspId);
		return desc;
	}

