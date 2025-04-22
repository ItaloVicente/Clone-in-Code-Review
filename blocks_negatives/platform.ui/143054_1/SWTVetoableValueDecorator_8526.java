	private Listener verifyListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			String currentText = (String) property.getValue(widget);
			String newText = currentText.substring(0, event.start) + event.text
					+ currentText.substring(event.end);
			if (!fireValueChanging(Diffs.createValueDiff(currentText, newText))) {
				event.doit = false;
			}
