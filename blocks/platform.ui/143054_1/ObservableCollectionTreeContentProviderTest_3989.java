		String[] rootElements = new String[] { "one", "two", "three" };
		final IObservableList<String> rootElementList = new WritableList<>(Arrays.asList(rootElements), null);

		contentProvider = new ObservableListTreeContentProvider<>((Object target) -> {
			if (target == input)
				return rootElementList;
			return null;
