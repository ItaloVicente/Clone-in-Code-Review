		for (MPartStack oldStack : oldStacks) {
			MStackElement element1 = oldStack.getSelectedElement();
			if (element1 instanceof MPlaceholder) {
				hiddenParts.add((MPart) ((MPlaceholder) element1).getRef());
			} else if (element1 instanceof MPart) {
				hiddenParts.add((MPart) element1);
