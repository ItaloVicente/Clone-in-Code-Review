	private List<MPart> getCloseableParts(MPart part, boolean hidden) {
		MElementContainer<MUIElement> container = getParent(part);
		if (container == null) {
			return new ArrayList<MPart>();
		}
		List<MPart> closeableSiblings = new ArrayList<MPart>();
		List<MUIElement> children = container.getChildren();
		for (MUIElement child : children) {
			if (!child.isToBeRendered()) {
				continue;
			}

			MPart otherPart = null;
			if (child instanceof MPart) {
				otherPart = (MPart) child;
			} else if (child instanceof MPlaceholder) {
				MUIElement otherItem = ((MPlaceholder) child).getRef();
				if (otherItem instanceof MPart) {
					otherPart = (MPart) otherItem;
				}
			}
			if (otherPart == null) {
				continue;
			}
			if (otherPart.isToBeRendered() && isClosable(otherPart)) {
				CTabItem item = findItemForPart(otherPart);
				boolean isHidden = item != null && !item.isShowing();
				if (isHidden == hidden) {
					closeableSiblings.add(otherPart);
				}
			}
		}
		return closeableSiblings;
	}

