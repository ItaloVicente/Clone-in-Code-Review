		for (MPerspectiveStack perspectiveStack : pStacks) {
			MPerspective selectedElement = perspectiveStack.getSelectedElement();
			MWindow win = selectedElement.getContext().get(MWindow.class);
			if (window.equals(win)) {
				return selectedElement;
			}
		}

