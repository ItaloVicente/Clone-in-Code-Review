	private void update() {
		if (window != null) {
			IPerspectiveDescriptor persp = null;
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				persp = page.getPerspective();
			}
			update(persp);
		}
	}
