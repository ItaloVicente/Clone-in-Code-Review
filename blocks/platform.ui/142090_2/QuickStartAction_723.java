		try {
			page.openEditor(input, EDITOR_ID);
		} catch (PartInitException e) {
			IDEWorkbenchPlugin
					.log("Error opening welcome editor for feature: " + feature.getFeatureId(), e); //$NON-NLS-1$
			IStatus status = new Status(
					IStatus.ERROR,
					IDEWorkbenchPlugin.IDE_WORKBENCH,
					1,
					IDEWorkbenchMessages.QuickStartAction_openEditorException, e);
			ErrorDialog
					.openError(
							workbenchWindow.getShell(),
							IDEWorkbenchMessages.Workbench_openEditorErrorDialogTitle,
							IDEWorkbenchMessages.Workbench_openEditorErrorDialogMessage,
							status);
			return false;
		}
		return true;
	}
