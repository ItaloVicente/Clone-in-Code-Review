	static class VirtualProvider extends
			StagingViewContentProvider implements ILazyTreeContentProvider {

		private TreeViewer treeViewer;

		VirtualProvider(StagingView stagingView,
				TreeViewer treeViewer, boolean unstagedSection) {
			super(stagingView, treeViewer, unstagedSection);
			this.treeViewer = treeViewer;
		}

		@Override
		public void updateElement(Object parent, int index) {
			Object[] children = getChildren(parent);
			if (index < children.length) {
				Object child = children[index];
				treeViewer.replace(parent, index, child);
				treeViewer.setChildCount(child, getChildren(child).length);
			}
		}

		@Override
		public void updateChildCount(Object element, int currentChildCount) {
			Object[] children = getChildren(element);
			int newChildCount = children.length;
			if (newChildCount != currentChildCount) {
				treeViewer.setChildCount(element, newChildCount);
			}
		}
	}

