	/**
	 * A Listener for a list of command groups, that updates the viewer and
	 * filter who are dependent on the action set selection.
	 * 
	 * @since 3.5
	 */
	private static final class ActionSetSelectionChangedListener implements
			ISelectionChangedListener {
		private final TreeViewer filterViewer;
		private final ActionSetFilter filter;

		public ActionSetSelectionChangedListener(TreeViewer viewer,
				ActionSetFilter menuStructureFilterByActionSet) {
			this.filterViewer = viewer;
			this.filter = menuStructureFilterByActionSet;
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			Object element = ((IStructuredSelection) event.getSelection())
					.getFirstElement();
			filter.setActionSet((ActionSet) element);
			filterViewer.refresh();
			filterViewer.expandAll();
		}
	}

	/**
	 * A filter which will only show action sets which contribute items in the
	 * given tree structure.
	 * 
	 * @since 3.5
	 */
	private static final class ShowUsedActionSetsFilter extends ViewerFilter {
		private DisplayItem rootItem;

		public ShowUsedActionSetsFilter(DisplayItem rootItem) {
			this.rootItem = rootItem;
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			return (includeInSetStructure(rootItem, (ActionSet) element));
		}
	}

