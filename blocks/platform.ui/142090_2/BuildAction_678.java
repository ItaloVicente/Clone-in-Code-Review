	boolean hasBuilder(IProject project) {
		if (!project.isAccessible())
			return false;
		try {
			ICommand[] commands = project.getDescription().getBuildSpec();
			if (commands.length > 0) {
				return true;
			}
		} catch (CoreException e) {
		}
		return false;
	}
