	private List<MPart> getCloseableSideParts(MPart part, boolean left) {
		MElementContainer<MUIElement> container = getParent(part);
		if (container == null) {
			return new ArrayList<MPart>();
		}

		List<MUIElement> children = container.getChildren();
		int thisPartIdx = children.indexOf(part);
		final int start = left ? 0 : thisPartIdx + 1;
		final int end = left ? thisPartIdx : children.size();

		return getCloseableSiblingParts(part, children, start, end);
	}

