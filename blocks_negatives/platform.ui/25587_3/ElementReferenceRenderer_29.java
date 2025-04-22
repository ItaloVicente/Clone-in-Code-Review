		int phLoc = modelService.getElementLocation(ph);
		if (phLoc == EModelService.IN_ACTIVE_PERSPECTIVE
				|| phLoc == EModelService.IN_SHARED_AREA
				|| phLoc == EModelService.OUTSIDE_PERSPECTIVE) {
			Control refWidget = (Control) ref.getWidget();
			if (refWidget == null) {
				ref.setToBeRendered(true);
				refWidget = (Control) renderingEngine.createGui(ref, newComp,
						getContextForParent(ref));
			} else {
				if (refWidget.getParent() != newComp) {
					refWidget.setParent(newComp);
				}
