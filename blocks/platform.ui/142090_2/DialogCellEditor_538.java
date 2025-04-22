	private Object value = null;

	static {
		ImageRegistry reg = JFaceResources.getImageRegistry();
		reg.put(CELL_EDITOR_IMG_DOTS_BUTTON, ImageDescriptor.createFromFile(
				DialogCellEditor.class, "images/dots_button.png"));//$NON-NLS-1$
	}

	private class DialogCellLayout extends Layout {
		@Override
