	private Listener shellReskinListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			viewer.refresh();
		}
	};
