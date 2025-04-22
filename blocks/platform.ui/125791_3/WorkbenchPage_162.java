			for (MPartStack oldStack : oldStacks) {
				MStackElement element = oldStack.getSelectedElement();
				if (element instanceof MPlaceholder) {
					hiddenParts.add((MPart) ((MPlaceholder) element).getRef());
				} else if (element instanceof MPart) {
					hiddenParts.add((MPart) element);
				}
