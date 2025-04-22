	private static final class BranchLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			RefNode refNode = (RefNode) element;
			return refNode.getObject().getName();
		}

		@Override
		public Image getImage(Object element) {
			return RepositoryTreeNodeType.REF.getIcon();
		}
	}

