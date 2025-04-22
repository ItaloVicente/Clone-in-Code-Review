	protected void handleHelpRequest(HelpEvent event) {
		Object oldData = event.data;
		event.data = this;
		fireHelpRequested(event);
		event.data = oldData;
	}
