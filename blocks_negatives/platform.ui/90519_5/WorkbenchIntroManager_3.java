		IWorkbenchWindow[] windows = this.workbench.getWorkbenchWindows();
		for (int i = 0; i < windows.length; i++) {
			WorkbenchWindow window = (WorkbenchWindow) windows[i];
			MUIElement introPart = window.modelService
.find(IIntroConstants.INTRO_VIEW_ID,
					window.getModel());
