				RenamableItem.Listener listener = new RenamableItem.Listener() {
					@Override
					public void handleChanged(RenamableItem item) {
						fireChangeEvent(Collections.singleton(item));
					}
				};
