		final DelegatingStyledCellLabelProvider labelProvider = new DelegatingStyledCellLabelProvider(
				new RepositoriesViewLabelProvider());
		PatternFilter filter = new PatternFilter() {
			@Override
			protected boolean isLeafMatch(Viewer viewer, Object element) {
				StyledString text = labelProvider.getStyledStringProvider()
						.getStyledText(element);
				return text != null && wordMatches(text.getString());
			}
		};
