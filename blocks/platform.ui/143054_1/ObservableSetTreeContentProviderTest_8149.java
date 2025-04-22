		final IObservableSet<Object> children = new WritableSet<>();
		final IObservableSet<Object> children2 = new WritableSet<>();
		initContentProvider(target -> {
			if (target == input)
				return children;
			if (target == input2)
				return children2;
			return null;
