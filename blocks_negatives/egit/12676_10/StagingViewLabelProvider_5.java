	public Image getImage(Object element) {
		final StagingEntry c = (StagingEntry) element;
		final DecorationResult decoration = new DecorationResult();
		decorationHelper.decorate(decoration, c);
		return getDecoratedImage(getEditorImage(c), decoration.getOverlay());
	}
