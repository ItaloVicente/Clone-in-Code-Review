		return monitor -> {
			IResource[] resources = (IResource[]) getActionResources()
					.toArray(new IResource[getActionResources().size()]);
			if (resources.length == 1) {
				IWorkspaceRoot workspaceRoot = resources[0].getWorkspace()
						.getRoot();
				IResource newResource = workspaceRoot.findMember(newPath);
				boolean go = true;
				if (newResource != null) {
					go = checkOverwrite(getShell(), newResource);
				}
				if (go) {
					MoveResourcesOperation op = new MoveResourcesOperation(
							resources[0],
							newPath,
							IDEWorkbenchMessages.RenameResourceAction_operationTitle);
					op.setModelProviderIds(getModelProviderIds());
					try {
						PlatformUI
								.getWorkbench()
								.getOperationSupport()
								.getOperationHistory()
								.execute(
										op,
										monitor,
										WorkspaceUndoUtil
												.getUIInfoAdapter(getShell()));
					} catch (ExecutionException e) {
						if (e.getCause() instanceof CoreException) {
							errorStatus[0] = ((CoreException) e.getCause())
									.getStatus();
						} else {
							errorStatus[0] = new Status(IStatus.ERROR,
									PlatformUI.PLUGIN_ID,
									getProblemsMessage(), e);
