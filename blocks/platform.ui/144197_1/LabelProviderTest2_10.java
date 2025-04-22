		list = new ListViewer(shell);
		ObservableListContentProvider<RenamableItem> contentProvider = new ObservableListContentProvider<>();
		list.setContentProvider(contentProvider);
		list.setLabelProvider(new ListeningLabelProvider<RenamableItem>(contentProvider.getKnownElements()) {
			RenamableItem.Listener listener = item -> fireChangeEvent(Collections.singleton(item));

			@Override
			public void updateLabel(ViewerLabel label, Object element) {
				if (element instanceof RenamableItem) {
					RenamableItem item = (RenamableItem) element;
