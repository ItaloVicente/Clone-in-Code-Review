
	private MWindow getParentWindow(MPartStack stack) {
		MElementContainer<?> parent = stack.getParent();
		while (parent != null && !(parent instanceof MWindow)) {
			parent = parent.getParent();
		}
		return parent instanceof MWindow ? ((MWindow) parent) : null;
	}
