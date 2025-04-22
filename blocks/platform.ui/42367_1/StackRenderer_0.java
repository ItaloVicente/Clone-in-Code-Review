	private int getPartIndex(MPart part, MElementContainer<MUIElement> container) {
		List<MUIElement> children = container.getChildren();
		for (int i = 0; i < children.size(); i++) {
			MUIElement child = children.get(i);
			MPart otherPart = null;
			if (child instanceof MPart) {
				otherPart = (MPart) child;
			} else if (child instanceof MPlaceholder) {
				MUIElement otherItem = ((MPlaceholder) child).getRef();
				if (otherItem instanceof MPart) {
					otherPart = (MPart) otherItem;
				}
			}
			if (otherPart == part) {
				return i;
			}
		}
		return -1;
	}

