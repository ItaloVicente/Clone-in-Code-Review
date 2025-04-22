
		if (minimizedElement instanceof MPartStack) {
			MPartStack theStack = (MPartStack) minimizedElement;
			MStackElement curSel = theStack.getSelectedElement();
			Control ctrl = (Control) minimizedElement.getWidget();

			if (ctrl instanceof CTabFolder && ((CTabFolder) ctrl).getSelection() == null) {
				theStack.setSelectedElement(null);
				theStack.setSelectedElement(curSel);
			}
		}

