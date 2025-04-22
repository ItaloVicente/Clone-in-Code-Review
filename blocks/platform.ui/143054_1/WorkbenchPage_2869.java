		for (MPartStack newStack : newStacks) {
			MStackElement element2 = newStack.getSelectedElement();
			if (element2 instanceof MPlaceholder) {
				visibleParts.add((MPart) ((MPlaceholder) element2).getRef());
			} else if (element2 instanceof MPart) {
				visibleParts.add((MPart) element2);
