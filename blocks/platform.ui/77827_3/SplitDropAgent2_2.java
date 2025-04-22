		if (relToElement instanceof MPartSashContainerElement) {
			MPartSashContainerElement element = (MPartSashContainerElement) relToElement;

			Control ctrl = (Control) relToElement.getWidget();
			Rectangle bb = ctrl.getBounds();
			bb = ctrl.getDisplay().map(ctrl.getParent(), null, bb);

			int side = Geometry.getClosestSide(bb, info.cursorPos);
			int emodelSideConstant = swtConstantToEmodelServiceConstant(side);

			dndManager.getModelService().insert(toInsert, element, emodelSideConstant, 0.5f);
		} else {
			System.out.println("Unknown drag element type: " + relToElement);
		}
