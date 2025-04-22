	private Control getPackParent(Control control) {
		Composite parent = control.getParent();
		while (parent != null) {
			if (parent instanceof CTabFolder) {
				Control topRight = ((CTabFolder) parent).getTopRight();
				if (topRight != null) {
					return topRight;
				}
				break;
			}
			parent = parent.getParent();
		}
		return control.getParent();
	}

	boolean isSelected(MPart part) {
		MElementContainer<?> parent = part.getParent();
		if (parent == null) {
			MPlaceholder placeholder = part.getCurSharedRef();
			if (placeholder == null) {
				return false;
			}

			parent = placeholder.getParent();
			return parent instanceof MGenericStack ? parent.getSelectedElement() == placeholder : parent != null;
		}
		return !(parent instanceof MGenericStack) || parent.getSelectedElement() == part;
	}

