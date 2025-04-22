				MElementContainer<MUIElement> phParent = elementToHide.getParent();
				if (phParent.getSelectedElement() == elementToHide) {
					phParent.setSelectedElement(null);
				}
				int vc = modelService.countRenderableChildren(phParent);
				if (vc == 0) {
					if (!modelService.isLastEditorStack(phParent))
						phParent.setToBeRendered(false);
				}
