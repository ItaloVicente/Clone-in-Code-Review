
		MUIElement hasContainerData = dragElement;
		while (hasContainerData != null
				&& (MUIElement) hasContainerData.getParent() instanceof MPartSashContainer == false) {
			hasContainerData = hasContainerData.getParent();
		}

