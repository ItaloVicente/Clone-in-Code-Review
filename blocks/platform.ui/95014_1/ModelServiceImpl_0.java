		List<MPlaceholder> elementsToHide = findElements(element, null, MPlaceholder.class, null);
		for (MPlaceholder elementToHide : elementsToHide) {
			if (elementToHide.getRef().getTags().contains(IPresentationEngine.NO_RESTORE)) {
				elementToHide.setToBeRendered(false);
				MElementContainer<MUIElement> phParent = elementToHide.getParent();
				if (phParent.getSelectedElement() == elementToHide) {
					phParent.setSelectedElement(null);
				}
				int vc = countRenderableChildren(phParent);
				if (vc == 0) {
					if (!isLastEditorStack(phParent))
						phParent.setToBeRendered(false);
				}
			}
		}
