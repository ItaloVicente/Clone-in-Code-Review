		if (element instanceof MPartStack && element.getRenderer() instanceof StackRenderer) {
			MPartStack stackModel = (MPartStack) element;

			MUIElement curSel = stackModel.getSelectedElement();

			if (curSel != null) {
				showElementRecursive(curSel);
			}
		}

