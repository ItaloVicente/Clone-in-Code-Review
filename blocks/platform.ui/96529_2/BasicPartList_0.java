	protected boolean deleteSelectedElement(Object selectedElement) {
		if (selectedElement == null) {
			selectedElement = getSelectedElement();
		}
		if (selectedElement instanceof MPart) {
			MPart part = (MPart) selectedElement;
			if (partService.savePart(part, true)) {
				partService.hidePart(part);
			}
