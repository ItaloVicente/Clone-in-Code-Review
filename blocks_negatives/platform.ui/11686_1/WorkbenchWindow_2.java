	private void hideNonRestorableViews() {
		List<MPart> sharedPartsToRemove = new ArrayList<MPart>();
		List<MPlaceholder> phList = modelService
				.findElements(model, null, MPlaceholder.class, null);
		for (MPlaceholder ph : phList) {
			if (!(ph.getRef() instanceof MPart))
				continue;

			String partId = ph.getElementId();

			int colonIndex = partId.indexOf(':');
			String descId = colonIndex == -1 ? partId : partId.substring(0, colonIndex);
			String secondaryId = colonIndex == -1 ? null : partId.substring(colonIndex + 1);
			IViewDescriptor regEntry = ((Workbench) workbench).getViewRegistry().find(descId);
				MElementContainer<MUIElement> phParent = ph.getParent();
				if (colonIndex != -1) {
					if (!sharedPartsToRemove.contains(ph.getRef()))
						sharedPartsToRemove.add((MPart) ph.getRef());
					ph.getParent().getChildren().remove(ph);
				} else if (ph.isToBeRendered()) {
					ph.setToBeRendered(false);
				}

				int vc = modelService.countRenderableChildren(phParent);
				if (vc == 0) {
					phParent.setToBeRendered(false);
