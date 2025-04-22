	private static void updateParentVisibility(MMenuElement menuElement) {
		MElementContainer<MUIElement> parent = menuElement.getParent();
		if (parent != null) {
			parent.setVisible(areChildsVisible(parent));
		}
	}

	private static boolean areChildsVisible(
			MElementContainer<MUIElement> container) {
		for (MUIElement child : container.getChildren()) {
			if (child.isVisible() && !(child instanceof MMenuSeparator)) {
				return true;
			}
		}
		return false;
	}

