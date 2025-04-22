		Control refWidget = (Control) ref.getWidget();
		if (refWidget == null) {
			ref.setToBeRendered(true);
			refWidget = (Control) renderingEngine.createGui(ref, newComp,
					getContextForParent(ref));
		} else {
			if (refWidget.getParent() != newComp) {
				refWidget.setParent(newComp);
