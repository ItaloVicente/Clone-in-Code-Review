		assertFiresPendingValueChange(new Runnable() {
			@Override
			public void run() {
				final Object value = delayed.getValue();
				assertEquals(newValue, value);
			}
