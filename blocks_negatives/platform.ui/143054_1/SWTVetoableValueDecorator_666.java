	private Listener disposeListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			SWTVetoableValueDecorator.this.dispose();
		}
	};
