			@Override
			public void handleEvent(Event event) {
				time.setEnabled(!syncTime.getSelection());
				if (syncTime.getSelection()) {
					runnable.run();
				}
