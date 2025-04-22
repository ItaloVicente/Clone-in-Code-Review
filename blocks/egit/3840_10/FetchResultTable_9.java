			public Image getImage(Object element) {
				return wrapped.getImage(element);
			}
		};
		treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
				styleProvider) {
