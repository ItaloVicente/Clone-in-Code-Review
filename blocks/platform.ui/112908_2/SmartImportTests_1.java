			processEventsUntil(new Condition() {
				@Override
				public boolean compute() {
					return okButton.isEnabled();
				}
			}, -1);
