			throw new ExecutionException(e.getMessage(), e);
		}

		if (operation == null) {
			return null;
		}
		Job job = new WorkspaceJob(DiscardChangesAction_discardChanges) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					operation.execute(monitor);
				} catch (CoreException e) {
					return createErrorStatus(e.getStatus().getMessage(), e);
				}
				return OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (DISCARD_CHANGES.equals(family)) {
					return true;
				}
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.setRule(operation.getSchedulingRule());
		job.schedule();
		return null;
	}

	private @Nullable DiscardChangesOperation createOperation(
			IWorkbenchPart part, ExecutionEvent event)
					throws ExecutionException, IOException {
		IResource[] selectedResources = GitFlowHandlerUtil.gatherResourceToOperateOn(event);
		String revision;
		try {
			revision = GitFlowHandlerUtil.gatherRevision(event);
		} catch (OperationCanceledException e) {
			return null;
