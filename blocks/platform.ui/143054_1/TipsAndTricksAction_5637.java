			} else {
				IStatus status = new Status(
						IStatus.ERROR,
						IDEWorkbenchPlugin.IDE_WORKBENCH,
						IStatus.INFO,
						IDEWorkbenchMessages.TipsAndTricksErrorDialog_noHref, null);
				ErrorDialog.openError(shell, IDEWorkbenchMessages.TipsAndTricksErrorDialog_title,
						IDEWorkbenchMessages.TipsAndTricksErrorDialog_noHref,
						status);
			}
		} else {
			IStatus status = new Status(IStatus.ERROR,
					IDEWorkbenchPlugin.IDE_WORKBENCH, IStatus.INFO, IDEWorkbenchMessages.TipsAndTricksErrorDialog_noHref, null);
			ErrorDialog.openError(shell, IDEWorkbenchMessages.TipsAndTricksErrorDialog_title,
					IDEWorkbenchMessages.TipsAndTricksErrorDialog_noFeatures,
					status);
		}
	}
