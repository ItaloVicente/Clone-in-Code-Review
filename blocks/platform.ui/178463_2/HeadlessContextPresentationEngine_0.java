		MUIElement current = element;
		while (current != null) {
			if (!current.isToBeRendered()) {
				return null;
			}
			if (current.getCurSharedRef() != null) {
				current = current.getCurSharedRef();
			} else {
				current = current.getParent();
			}
