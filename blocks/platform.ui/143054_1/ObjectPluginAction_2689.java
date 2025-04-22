		if (activePart != null) {
			stopPartListening();
			activePart = null;
		}
		super.dispose();
	}

	private void stopPartListening() {
		if (activePart != null) {
			IWorkbenchPage page = activePart.getSite().getPage();
			if (page != null) {
				page.removePartListener(this);
			}
		}
	}
