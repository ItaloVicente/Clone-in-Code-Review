	private void closeSiblingParts(MPart part, boolean skipThisPart) {
		MElementContainer<MUIElement> container = getParent(part);
		if (container == null)
			return;

		List<MPart> others = getCloseableSiblingParts(part);

		if (!skipThisPart && part.isToBeRendered() && isClosable(part)) {
			others.add(part);
		}

		MUIElement selectedElement = container.getSelectedElement();
		if (others.remove(selectedElement)) {
			others.add((MPart) selectedElement);
		} else if (selectedElement instanceof MPlaceholder) {
			selectedElement = ((MPlaceholder) selectedElement).getRef();
			if (others.remove(selectedElement)) {
				others.add((MPart) selectedElement);
			}
		}

		EPartService partService = getContextForParent(part).get(
				EPartService.class);
		for (MPart otherPart : others) {
			if (partService.savePart(otherPart, true))
				partService.hidePart(otherPart);
		}
	}

