			return new TableColumnViewerLabelProvider<E>(labelProvider);
		if (labelProvider instanceof CellLabelProvider) {
			@SuppressWarnings("unchecked")
			CellLabelProvider<E> cellLabelProvider = (CellLabelProvider<E>) labelProvider;
			return cellLabelProvider;
		}
		return new WrappedViewerLabelProvider<E>(labelProvider);
