	private MUIElement getParentModel() {
		MElementContainer<MUIElement> parent = part.getParent();
		if (parent == null) {
			MPlaceholder placeholder = part.getCurSharedRef();
			return placeholder == null ? null : placeholder.getParent();
		}
		return parent;
	}

	private boolean isOnTop() {
		MUIElement parentModel = getParentModel();
		if (parentModel.getRenderer() instanceof StackRenderer) {
			MPartStack stack = (MPartStack) parentModel;
			if (stack.getSelectedElement() == part)
				return true;
			if (stack.getSelectedElement() instanceof MPlaceholder) {
				MPlaceholder ph = (MPlaceholder) stack.getSelectedElement();
				return ph.getRef() == part;
			}
		}

		return true;
	}

