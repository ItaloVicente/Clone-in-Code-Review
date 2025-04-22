		MUIElement hasSizeData = dragElement;
		while (hasSizeData != null
				&& (MUIElement) hasSizeData.getParent() instanceof MPartSashContainer == false) {
			hasSizeData = hasSizeData.getParent();
		}

		if (hasSizeData != null) {
			PartSizeInfo.copy(toInsert, hasSizeData);
			dndManager.getModelService().insert(toInsert, (MPartSashContainerElement) relToElement,
					where, ratio);
		} else {
			PartSizeInfo.copy(toInsert, originalParent);
		}
