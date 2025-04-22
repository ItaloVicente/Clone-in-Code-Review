			String message = UIText.WizardProjectImportPage_errorMessage;
			IStatus status;
			if (t instanceof CoreException) {
				status = ((CoreException) t).getStatus();
			} else {
				status = new Status(IStatus.ERROR,
						IDEWorkbenchPlugin.IDE_WORKBENCH, 1, message, t);
			}
			Activator.logError(message, t);
			ErrorDialog.openError(getShell(), message, null, status);
