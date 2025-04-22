	private Listener activationListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			MPart part = (MPart) event.widget.getData(OWNING_ME);
			if (part != null) {
				try {
					partToActivate = part;
					activate(partToActivate);
				} finally {
					partToActivate = null;
				}
