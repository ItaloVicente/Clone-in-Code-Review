		MPerspectiveStack perspStack = (MPerspectiveStack) changedElement;
		if (!perspStack.isToBeRendered())
			return;

		MPerspective selElement = perspStack.getSelectedElement();
		for (ToolItem ti : psTB.getItems()) {
			ti.setSelection(ti.getData() == selElement);
