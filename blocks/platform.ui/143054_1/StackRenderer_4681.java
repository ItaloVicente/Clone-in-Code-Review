			addItemToSet(itemsToSet, part);
		} else if (partParent instanceof MPartSashContainer) {
			MElementContainer<MUIElement> parentParent = partParent.getParent();
			if (parentParent instanceof MPart) {
				MPart parentParentMPart = (MPart) parentParent;
				addItemToSet(itemsToSet, parentParentMPart);
