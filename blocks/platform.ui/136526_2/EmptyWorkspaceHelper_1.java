	private static class ImportAction extends Action {
		@Override
		public void run() {
			IHandlerService handlerService = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getService(IHandlerService.class);
			try {
				handlerService.executeCommand("org.eclipse.ui.file.import", null); //$NON-NLS-1$
			} catch (Exception ex) {
				IDEWorkbenchPlugin.log(this.getClass(), "run", ex); //$NON-NLS-1$
			}
		}
	}

