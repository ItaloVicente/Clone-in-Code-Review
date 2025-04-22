		IWorkbenchPart part;
		if (event != null)
			part = getPart(event);
		else
			part = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActivePart();

