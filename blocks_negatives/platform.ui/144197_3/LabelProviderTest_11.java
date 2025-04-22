			setOfRenamables = new WritableSet<>();

			list = new ListViewer(shell);
			ObservableSetContentProvider<RenamableItem> contentProvider = new ObservableSetContentProvider<>();
			list.setContentProvider(contentProvider);
			list.setLabelProvider(new ListeningLabelProvider<RenamableItem>(contentProvider.getKnownElements()) {
				RenamableItem.Listener listener = item -> fireChangeEvent(Collections.singleton(item));

				@Override
				public void updateLabel(ViewerLabel label, Object element) {
					if (element instanceof RenamableItem) {
						RenamableItem item = (RenamableItem) element;

						label.setText(item.getName());
					}
				}
