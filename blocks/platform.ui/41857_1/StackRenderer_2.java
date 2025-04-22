	private void closeParts(MPart part, boolean hidden) {
		MElementContainer<MUIElement> container = getParent(part);
		if (container == null) {
			return;
		}
		List<MPart> others = getCloseableParts(part, hidden);
		closeSiblingParts(part, others, true);
	}

