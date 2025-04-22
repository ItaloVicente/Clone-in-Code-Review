	private void notifyListeners(int index) {
		if (index >= 0) {
			Event event = new Event();
			event.type = SWT.Selection;
			event.index = index;
			event.widget = this;
			event.data = fileRevisions[index];
			for (Listener toNotify : eventList) {
				toNotify.handleEvent(event);
			}
