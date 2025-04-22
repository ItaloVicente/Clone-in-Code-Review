		public void handleEvent(Event event) {
			if (event.detail == SWT.CHECK) {
				handleCheckbox(event);
			} else {
				handleSelection();
			}
