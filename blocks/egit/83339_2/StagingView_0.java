		private final boolean fromUnstaged;

		public StagingDragSelection(IStructuredSelection original,
				boolean fromUnstaged) {
			this.delegate = original;
			this.fromUnstaged = fromUnstaged;
		}

		@Override
		public boolean isEmpty() {
			return delegate.isEmpty();
		}

		@Override
		public Object getFirstElement() {
			return delegate.getFirstElement();
		}

		@Override
		public Iterator iterator() {
			return delegate.iterator();
		}

		@Override
		public int size() {
			return delegate.size();
		}

		@Override
		public Object[] toArray() {
			return delegate.toArray();
		}

		@Override
		public List toList() {
			return delegate.toList();
		}

		public boolean isFromUnstaged() {
			return fromUnstaged;
		}
	}

	private static class StagingDragListener extends DragSourceAdapter {

		private final ISelectionProvider provider;

		private final StagingViewContentProvider contentProvider;

		private final boolean unstaged;

		public StagingDragListener(ISelectionProvider provider,
				StagingViewContentProvider contentProvider, boolean unstaged) {
