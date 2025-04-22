			processEventsUntil(new Condition() {

				@Override
				public boolean compute() {
					return window.getActivePage().getActivePart() != null;
				}

			}, 10000);
