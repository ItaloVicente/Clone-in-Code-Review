		setOfRenamables = new WritableSet<>();

		list = new ListViewer(shell);
		ObservableSetContentProvider<RenamableItem> contentProvider = new ObservableSetContentProvider<>();
		list.setContentProvider(contentProvider);
		list.setLabelProvider(new ListeningLabelProvider<RenamableItem>(contentProvider.getKnownElements()) {
			RenamableItem.Listener listener = item -> fireChangeEvent(Collections.singleton(item));
