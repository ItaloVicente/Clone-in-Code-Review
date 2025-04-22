	private void stopPartListening() {
		if (currentPart != null) {
			IWorkbenchPage page = currentPart.getSite().getPage();
			if (page != null) {
				page.removePartListener(this);
			}
		}
	}

