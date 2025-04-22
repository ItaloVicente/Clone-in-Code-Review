
		IWorkbenchPart part = getPart(event);
		String question = UIText.DiscardChangesAction_confirmActionMessage;
		ILaunchConfiguration launch = LaunchFinder
				.getRunningLaunchConfiguration(Arrays.asList(getRepositories()),
						null);
		if (launch != null) {
			question = MessageFormat.format(question,
							UIText.LaunchFinder_RunningLaunchMessage,
							launch.getName()));
		} else {
			question = MessageFormat.format(question, ""); //$NON-NLS-1$
		}
		boolean performAction = MessageDialog.openConfirm(getShell(event),
				UIText.DiscardChangesAction_confirmActionTitle,
				question);
		if (!performAction)
			return null;
		final DiscardChangesOperation operation = createOperation(part, event);
		if (operation == null)
			return null;
		String jobname = UIText.DiscardChangesAction_discardChanges;
		Job job = new WorkspaceJob(jobname) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					operation.execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getStatus()
							.getMessage(), e);
				}
				return Status.OK_STATUS;
