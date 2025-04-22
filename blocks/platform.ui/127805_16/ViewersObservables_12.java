	public static <T> IObservableValue<T> observeSingleSelection(ISelectionProvider selectionProvider,
			Class<T> elementType) {
		checkNull(selectionProvider);
		return ViewerProperties.singleSelection(elementType).observe(selectionProvider);
	}

