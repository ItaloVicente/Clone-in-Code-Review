		Control ctrl = (Control) relToElement.getWidget();
		Rectangle bb = ctrl.getBounds();
		bb = ctrl.getDisplay().map(ctrl.getParent(), null, bb);

		int side = Geometry.getRelativePosition(bb, info.cursorPos);

		dndManager.getModelService().insert(toInsert, (MPartSashContainerElement) relToElement, side, 0.5f);
