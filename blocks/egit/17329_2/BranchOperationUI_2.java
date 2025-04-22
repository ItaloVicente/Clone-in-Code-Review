
	private boolean shouldCancelBecauseOfRunningLaunches() {
		if (mode == MODE_CHECKOUT)
			return false;
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (!store
				.getBoolean(UIPreferences.SHOW_RUNNING_LAUNCH_ON_CHECKOUT_WARNING))
			return false;

		ILaunchConfiguration launchConfiguration = getRunningLaunchConfiguration();
		if (launchConfiguration != null) {
			String[] buttons = new String[] {
					UIText.BranchOperationUI_Continue,
					IDialogConstants.CANCEL_LABEL };
			String message = NLS.bind(UIText.BranchOperationUI_RunningLaunchMessage,
					launchConfiguration.getName());
			MessageDialogWithToggle continueDialog = new MessageDialogWithToggle(
					getShell(), UIText.BranchOperationUI_RunningLaunchTitle,
					null, message, MessageDialog.NONE, buttons, 0,
					UIText.BranchOperationUI_RunningLaunchDontShowAgain, false);
			int result = continueDialog.open();
			if (result == IDialogConstants.CANCEL_ID || result == SWT.DEFAULT)
				return true;
			boolean dontWarnAgain = continueDialog.getToggleState();
			if (dontWarnAgain)
				store.setValue(
						UIPreferences.SHOW_RUNNING_LAUNCH_ON_CHECKOUT_WARNING,
						false);
		}
		return false;
	}

	private ILaunchConfiguration getRunningLaunchConfiguration() {
		Set<IProject> projects = new HashSet<IProject>(
				Arrays.asList(ProjectUtil.getProjects(repository)));

		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		ILaunch[] launches = launchManager.getLaunches();
		for (ILaunch launch : launches) {
			if (launch.isTerminated())
				continue;
			ISourceLocator locator = launch.getSourceLocator();
			if (locator instanceof ISourceLookupDirector) {
				ISourceLookupDirector director = (ISourceLookupDirector) locator;
				ISourceContainer[] containers = director.getSourceContainers();
				if (isAnyProjectInSourceContainers(containers, projects))
					return launch.getLaunchConfiguration();
			}
		}
		return null;
	}

	private boolean isAnyProjectInSourceContainers(
			ISourceContainer[] containers, Set<IProject> projects) {
		for (ISourceContainer container : containers) {
			if (container instanceof ProjectSourceContainer) {
				ProjectSourceContainer projectContainer = (ProjectSourceContainer) container;
				if (projects.contains(projectContainer.getProject()))
					return true;
			}
			try {
				boolean found = isAnyProjectInSourceContainers(
						container.getSourceContainers(), projects);
				if (found)
					return true;
			} catch (CoreException e) {
			}
		}
		return false;
	}

