		listOfRenamables = new WritableList<>();

		list = new ListViewer(shell);
		ObservableListContentProvider<RenamableItem> contentProvider = new ObservableListContentProvider<>();
		list.setContentProvider(contentProvider);
		list.setLabelProvider(new ListeningLabelProvider<RenamableItem>(contentProvider.getKnownElements()) {
			RenamableItem.Listener listener = item -> fireChangeEvent(Collections.singleton(item));
