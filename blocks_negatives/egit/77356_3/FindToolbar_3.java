	private void sendEvent(Widget widget, int index) {
		Event event = new Event();
		event.type = SWT.Selection;
		event.index = index;
		event.widget = widget;
		event.data = fileRevisions[index];
		for (Listener listener : eventList) {
			listener.handleEvent(event);
