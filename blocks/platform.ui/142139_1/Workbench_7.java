	public static Object setRestartArguments(String workspacePath) {
		String property = System.getProperty(Workbench.PROP_VM);
		if (property == null) {
			MessageDialog.openError(null, WorkbenchMessages.Workbench_problemsRestartErrorTitle,
					NLS.bind(WorkbenchMessages.Workbench_problemsRestartErrorMessage, Workbench.PROP_VM));
			return IApplication.EXIT_OK;
		}
		String command_line = Workbench.buildCommandLine(workspacePath);
		if (command_line == null) {
			return IApplication.EXIT_OK;
		}

		System.setProperty(Workbench.PROP_EXIT_CODE, IApplication.EXIT_RELAUNCH.toString());
		System.setProperty(Workbench.PROP_EXIT_DATA, command_line);
		return IApplication.EXIT_RELAUNCH;
	}

