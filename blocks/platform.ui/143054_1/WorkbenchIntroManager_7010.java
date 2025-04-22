		IWorkbenchWindow window = viewPart.getSite().getWorkbenchWindow();
		if (window.equals(testWindow)) {
			return true;
		}
		return false;
	}

	private void createIntro(IWorkbenchWindow preferredWindow) {
		if (this.workbench.getIntroDescriptor() == null) {
