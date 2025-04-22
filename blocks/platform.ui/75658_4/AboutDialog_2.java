
		final IContextService contextService = PlatformUI.getWorkbench().getService(IContextService.class);
		final Listener listener = e -> {
			if (SWT.Activate == e.type) {
				contextActivation = contextService.activateContext(ID_CONTEXT);
			} else if (SWT.Deactivate == e.type) {
				contextService.deactivateContext(contextActivation);
			}
		};
		newShell.addListener(SWT.Activate, listener);
		newShell.addListener(SWT.Deactivate, listener);
		newShell.addListener(SWT.Dispose, e -> {
			contextService.deactivateContext(contextActivation);
			newShell.removeListener(SWT.Activate, listener);
			newShell.removeListener(SWT.Deactivate, listener);
		});
