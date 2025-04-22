		} else if (isEditorStack() || minimizedElement instanceof MPlaceholder) {
			trimStackTB.getItem(1).setSelection(true);
		} else if (isPerspectiveStack()) {
			MPerspectiveStack pStack = (MPerspectiveStack) minimizedElement;
			MUIElement selElement = pStack.getSelectedElement();
			for (ToolItem item : trimStackTB.getItems()) {
				item.setSelection(item.getData() == selElement);
			}
