		MUIElement hasContainerData = dragElement;
		while (hasContainerData != null
				&& (MUIElement) hasContainerData.getParent() instanceof MPartSashContainer == false) {
			hasContainerData = hasContainerData.getParent();
		}

		if (hasContainerData != null) {
			toInsert.setContainerData(hasContainerData.getContainerData());
			dndManager.getModelService().insert(toInsert, (MPartSashContainerElement) relToElement,
					where, ratio);
		} else {
			toInsert.setContainerData(originalParent.getContainerData());
		}
