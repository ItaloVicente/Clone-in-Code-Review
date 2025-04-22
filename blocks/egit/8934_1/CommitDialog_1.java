		PatternFilter patternFilter = new PatternFilter() {
			@Override
			protected boolean isLeafMatch(Viewer viewer, Object element) {
				if(element instanceof CommitItem) {
					CommitItem commitItem = (CommitItem) element;
					return wordMatches(commitItem.path);
				}
				return super.isLeafMatch(viewer, element);
			}
		};
		patternFilter.setIncludeLeadingWildcard(true);
		FilteredCheckboxTree resourcesTreeComposite = new FilteredCheckboxTree(
				filesArea, toolkit, SWT.FULL_SELECTION, patternFilter);
		Tree resourcesTree = resourcesTreeComposite.getViewer().getTree();
		resourcesTree.setData(FormToolkit.KEY_DRAW_BORDER,
