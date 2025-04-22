		int phLoc = modelService.getElementLocation(ph);
		boolean isOutsidePerspective = (phLoc & EModelService.OUTSIDE_PERSPECTIVE) != 0;
		boolean isInActivePerspective = (phLoc & EModelService.IN_ACTIVE_PERSPECTIVE) != 0;
		if (isOutsidePerspective || isInActivePerspective) {
			Control refWidget = (Control) ref.getWidget();
			if (refWidget == null) {
				ref.setToBeRendered(true);
				refWidget = (Control) renderingEngine.createGui(ref, newComp,
						getContextForParent(ref));
			} else {
				if (refWidget.getParent() != newComp) {
					refWidget.setParent(newComp);
				}
