		actionBar.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (indexListener == null) {
					return;
				}
				int detail = event.detail;
				if (detail == SWT.TRAVERSE_ARROW_NEXT) {
					indexListener.selectNext();
				}
				if (detail == SWT.TRAVERSE_ARROW_PREVIOUS) {
					indexListener.selectPrevious();
				}

