	private Listener shellReskinListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			viewer.refresh();
			refreshControl(messageBodyComposite);
			messageText.setBackground(viewer.getTable().getBackground());
		}
