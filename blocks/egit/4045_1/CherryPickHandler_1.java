		Job job = new Job(MessageFormat.format(
				UIText.CherryPickHandler_JobName, commit.name())) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
					CherryPickResult cherryPickResult = op.getResult();
					RevCommit newHead = cherryPickResult.getNewHead();
					if (newHead != null
							&& cherryPickResult.getCherryPickedRefs().isEmpty())
						showNotPerformedDialog(parent);
					if (newHead == null) {
						CherryPickStatus status = cherryPickResult.getStatus();
						switch (status) {
						case CONFLICTING:
							showConflictDialog(parent);
							break;
						case FAILED:
							showFailure(cherryPickResult);
							break;
						case OK:
							break;
						}
					}
				} catch (CoreException e) {
					Activator.logError(
							UIText.CherryPickOperation_InternalError, e);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.CHERRY_PICK.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.schedule();
		return null;
	}

	private void showNotPerformedDialog(final Shell shell) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			public void run() {
				MessageDialog.openWarning(shell,
