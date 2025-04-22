		IHandlerService handlerService = PlatformUI.getWorkbench()
				.getService(IHandlerService.class);
		UIThreadRunnable.asyncExec(new VoidResult() {
			@Override
			public void run() {
				try {
					handlerService.executeCommand(commandId, null);
				} catch (Exception e) {
					throw new RuntimeException(
							"Failed to execute the command - " + commandId, e); //$NON-NLS-1$
				}
			}
		});
