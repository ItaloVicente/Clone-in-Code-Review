			for (MPartStack newStack : newStacks) {
				MStackElement element = newStack.getSelectedElement();
				if (element instanceof MPlaceholder) {
					visibleParts.add((MPart) ((MPlaceholder) element).getRef());
				} else if (element instanceof MPart) {
					visibleParts.add((MPart) element);
				}
