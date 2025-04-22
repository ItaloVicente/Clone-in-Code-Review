	private Listener resizeListener = new Listener() {

		@Override
		public void handleEvent(Event event) {
			if (!inupdateMode) {
				updateColumnData(event.widget);
			}
