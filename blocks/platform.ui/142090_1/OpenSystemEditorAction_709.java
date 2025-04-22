		try {
			getWorkbenchPage().openEditor(new FileEditorInput(file),
					IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
		} catch (PartInitException e) {
			DialogUtil.openError(getWorkbenchPage().getWorkbenchWindow()
					.getShell(), IDEWorkbenchMessages.OpenSystemEditorAction_dialogTitle,
					e.getMessage(), e);
		}
	}
