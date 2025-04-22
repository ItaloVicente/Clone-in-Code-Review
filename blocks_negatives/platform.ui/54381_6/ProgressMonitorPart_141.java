    protected Listener fCancelListener = new Listener() {
        @Override
		public void handleEvent(Event e) {
            setCanceled(true);
            if (fCancelComponent != null) {
				fCancelComponent.setEnabled(false);
			}
        }
    };
