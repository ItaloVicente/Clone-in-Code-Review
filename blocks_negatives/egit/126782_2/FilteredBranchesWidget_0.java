		PatternFilter filter = new PatternFilter() {
			@Override
			protected boolean isLeafMatch(Viewer viewer, Object element) {
				TreeViewer treeViewer = (TreeViewer) viewer;
				int numberOfColumns = treeViewer.getTree().getColumnCount();
				boolean isMatch = false;
				for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
					ColumnLabelProvider labelProvider = (ColumnLabelProvider) treeViewer
							.getLabelProvider(columnIndex);
					String labelText = labelProvider.getText(element);
					isMatch |= wordMatches(labelText);
				}
				return isMatch;
			}
		};
		filter.setIncludeLeadingWildcard(true);

