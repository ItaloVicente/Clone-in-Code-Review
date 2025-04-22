
	private void stopPartListening() {
		if (activePart != null) {
			IWorkbenchPage page = activePart.getSite().getPage();
			if (page != null) {
				page.removePartListener(this);
			}
		}
	}
