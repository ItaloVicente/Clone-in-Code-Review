			return new TableColumnViewerLabelProvider<E,I>(labelProvider);
		if (labelProvider instanceof CellLabelProvider) {
			@SuppressWarnings("unchecked")
			CellLabelProvider<E, I> cellLabelProvider = (CellLabelProvider<E, I>) labelProvider;
			return cellLabelProvider;
		}
		return new WrappedViewerLabelProvider<E,I>(labelProvider);
