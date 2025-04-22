	private void updateSelectionAndParentVisibility(MUIElement element) {
		MElementContainer<MUIElement> parent = element.getParent();
		if (parent.getSelectedElement() == element) {
			parent.setSelectedElement(null);
		}
		int renderableChildren = modelService.countRenderableChildren(parent);
		if (renderableChildren == 0 && !modelService.isLastEditorStack(parent)) {
			parent.setToBeRendered(false);
			updateSelectionAndParentVisibility(parent);
		}
	}

