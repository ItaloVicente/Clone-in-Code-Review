
	class ItemLabelProvider implements ILabelProvider {
		public org.eclipse.swt.graphics.Image getImage(Object element) {
			return RepositoryTreeNodeType.REF.getIcon();
		}
		public void dispose() {
		}
		public String getText(Object element) {
			return (String) element;
		}
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}
		public void addListener(ILabelProviderListener listener) {
		}
		public void removeListener(ILabelProviderListener listener) {
		}
	}
