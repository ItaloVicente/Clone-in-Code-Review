		final IObservableValue beanViewerSelection = ViewersObservables
				.observeSingleSelection(beanViewer);
		IObservableValue beanSelected = new ComputedValue(Boolean.TYPE) {
			@Override
			protected Object calculate() {
				return Boolean.valueOf(beanViewerSelection.getValue() != null);
			}
		};
