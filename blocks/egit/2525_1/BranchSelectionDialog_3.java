		deleteteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent selectionEvent) {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow activeWorkbenchWindow = workbench
						.getActiveWorkbenchWindow();
				IHandlerService hsr = (IHandlerService) activeWorkbenchWindow
						.getService(IHandlerService.class);
				IEvaluationContext ctx = hsr.getCurrentState();
				ctx.addVariable(ACTIVE_CURRENT_SELECTION_NAME,
						branchTree.getSelection());

				ICommandService commandService = (ICommandService) activeWorkbenchWindow
						.getService(ICommandService.class);
				Command deleteCommand = commandService
						.getCommand("org.eclipse.egit.ui.RepositoriesViewDeleteBranch"); //$NON-NLS-1$

				deleteCommand.addExecutionListener(new IExecutionListener() {
					public void preExecute(String commandId,
							ExecutionEvent event) {	/* do nothing */ }

					public void postExecuteSuccess(String commandId,
							Object returnValue) {
						branchTree.refresh();
					}

					public void postExecuteFailure(String commandId,
							ExecutionException exception) { /* do nothing */  }

					public void notHandled(String commandId,
							NotHandledException exception) { /* do nothing */ }
				});

				ExecutionEvent executionEvent = hsr.createExecutionEvent(
						deleteCommand, null);
				try {
					deleteCommand.executeWithChecks(executionEvent);
				} catch (Throwable e) {
					reportError(
							e,
							UIText.BranchSelectionDialog_ErrorCouldNotDeleteRef,
							refNameFromDialog());
				}
			}
		});

