
	private boolean isEmptyStackOnTopWindow(MUIElement element) {
		return element instanceof MPartStack && ((MPartStack) element).getChildren().isEmpty()
				&& geWindow(element) == getTopLevelWindow(element);
	}

	private MWindow getTopLevelWindow(MUIElement element) {
		return modelService.getTopLevelWindowFor(element);
	}

	private MWindow geWindow(MUIElement element) {
		MElementContainer<?> parent = element.getParent();
		while (parent != null && !(parent instanceof MWindow)) {
			parent = parent.getParent();
		}
		return parent instanceof MWindow ? ((MWindow) parent) : null;
	}
