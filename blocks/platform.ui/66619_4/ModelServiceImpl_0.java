		for (MPerspectiveStack perspectiveStack : pStacks) {
			MPerspective selectedElement = perspectiveStack.getSelectedElement();
			if (selectedElement != null) {
				MWindow win = selectedElement.getContext().get(MWindow.class);
				if (window.equals(win)) {
					return selectedElement;
				}
			}
		}

