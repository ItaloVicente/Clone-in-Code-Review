			};
			try {
				ps.runInUI(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), runnable, null);
			} catch (InvocationTargetException e) {
				Throwable cause = e.getCause();
				IStatus status;
				if (cause instanceof CoreException) {
					status = ((CoreException) cause).getStatus();
				} else {
					status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
							"Error while retrieving native editors", cause); //$NON-NLS-1$
				}
				StatusManager.getManager().handle(status);
			} catch (InterruptedException e) {
