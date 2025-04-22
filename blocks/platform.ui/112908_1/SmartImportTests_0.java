			final Button okButton = getFinishButton(dialog.buttonBar);
			assertNotNull(okButton);
			processEventsUntil(new Condition() {
				@Override
				public boolean compute() {
					return okButton.isEnabled();
				}
			}, -1);
