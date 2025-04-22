	private boolean reportException(CoreException e) {
		final IStatus status = e.getStatus();

		if ((status.getException() instanceof org.eclipse.jgit.api.errors.TransportException)
				&& ("Nothing to fetch.".equalsIgnoreCase(status.getException() //$NON-NLS-1$
						.getMessage()))) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();

					ErrorDialog.openError(shell,
							UIText.FetchOperationUI_FetchFailed,
							UIText.FetchOperationUI_NothingToFetch, status);
				}
			});

			return true;
		}

		return false;
	}

