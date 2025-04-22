		if (workbenchWindow == null) {
			return;
		}
		super.partDeactivated(null);
		workbenchWindow.removePageListener(this);
		workbenchWindow.getPartService().removePartListener(this);
		workbenchWindow = null;
	}
