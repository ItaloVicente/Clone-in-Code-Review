			event.doit = false;
		}
	}

	boolean hasBuilder(IProject project) {
		try {
			ICommand[] commands = project.getDescription().getBuildSpec();
			if (commands.length > 0) {
