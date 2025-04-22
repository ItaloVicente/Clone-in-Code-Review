	private Listener escapeListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			if (event.character == SWT.ESC) {
				showStack(false);
				partService.requestActivation();
			}
		}
	};
