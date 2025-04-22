		final IObservableList<Object> children = new WritableList<>();
		final IObservableList<Object> children2 = new WritableList<>();
		initContentProvider(target -> {
			if (target == input)
				return children;
			if (target == input2)
				return children2;
			return null;
