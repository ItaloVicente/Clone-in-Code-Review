		ISelection selection;
		if (event != null)
			selection = HandlerUtil.getCurrentSelectionChecked(event);
		else
			selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getSelection();
