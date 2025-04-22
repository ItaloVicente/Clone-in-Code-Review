
	private static class DeferredBatchLoader
			extends DeferredTreeContentManager {

		private AbstractTreeViewer viewer;

		public DeferredBatchLoader(AbstractTreeViewer viewer) {
			super(viewer);
			this.viewer = viewer;
		}

		@Override
		protected void addChildren(Object parent, Object[] children,
				IProgressMonitor monitor) {
			Control control = viewer.getControl();
			if (control == null || control.isDisposed()) {
				return;
			}
			control.getDisplay().syncExec(() -> {
				if (!control.isDisposed()) {
					if (children.length != 1 || children[0] != ERROR_ELEMENT) {
						viewer.remove(ERROR_ELEMENT);
					}
					viewer.add(parent, children);
				}
			});
		}
	}
