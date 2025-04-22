	private MPartStack getActiveStack(Object element) {
		if (element instanceof MPartStack) {
			return (MPartStack) element;
		} else if (element instanceof MElementContainer<?>) {
			return getActiveStack(((MElementContainer<?>) element).getSelectedElement());
		}
