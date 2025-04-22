		return getCloseableSiblingParts(part, children, 0, children.size());
	}

	private List<MPart> getCloseableSiblingParts(MPart part, List<MUIElement> children,
			final int start, final int end) {
		List<MPart> closeableSiblings = new ArrayList<MPart>();
		for (int i = start; i < end; i++) {
			MUIElement child = children.get(i);
