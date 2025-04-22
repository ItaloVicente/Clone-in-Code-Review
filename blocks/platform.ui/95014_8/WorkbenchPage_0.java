		MUIElement clone = modelService.cloneElement(visiblePerspective, application);
		MWindow window = WorkbenchWindow.class.cast(getActivePart().getSite().getWorkbenchWindow()).getModel();
		ModelServiceImpl.class.cast(modelService).getNullRefPlaceHolders(clone, window);
		List<MPlaceholder> elementsToHide = modelService.findElements(clone, null, MPlaceholder.class, null);
		for (MPlaceholder elementToHide : elementsToHide) {
			if (elementToHide.getRef().getTags().contains(IPresentationEngine.NO_RESTORE)) {
				elementToHide.setToBeRendered(false);
				MElementContainer<MUIElement> phParent = elementToHide.getParent();
				if (phParent.getSelectedElement() == elementToHide) {
					phParent.setSelectedElement(null);
				}
				int vc = modelService.countRenderableChildren(phParent);
				if (vc == 0) {
					if (!modelService.isLastEditorStack(phParent))
						phParent.setToBeRendered(false);
				}
			}
		}
