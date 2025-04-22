	public Image getColumnImage(final Object element, final int columnIndex) {
		if (columnIndex == 0) {
			final FileDiff c = (FileDiff) element;
			return (Image) resourceManager.get(c.getImageDescriptor(c));
		}
		return null;
