		resetMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (perspSwitcherToolbar.isDisposed())
					return;
				IHandlerService handlerService = workbenchWindow.getService(IHandlerService.class);
				IStatus status = Status.OK_STATUS;
				try {
					handlerService.executeCommand(IWorkbenchCommandConstants.WINDOW_RESET_PERSPECTIVE, null);
				} catch (ExecutionException e) {
					status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, e.getMessage(), e);
				} catch (NotDefinedException e) {
					status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, e.getMessage(), e);
				} catch (NotEnabledException e) {
					status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, e.getMessage(), e);
				} catch (NotHandledException e) {
				}
				if (!status.isOK())
					StatusManager.getManager().handle(status,
							StatusManager.SHOW | StatusManager.LOG);
