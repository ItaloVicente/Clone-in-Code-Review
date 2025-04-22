		if (side != null) {
			MTrimElement trimElement = (MTrimElement) dragElement;
			if (trimElement.getTags().contains(IPresentationEngine.NO_DETACH)) {
				MTrimmedWindow window = (MTrimmedWindow) dndManager.getDragWindow();
				MTrimBar sideTrimBar = dndManager.getModelService().getTrim(window, side);

				return (MUIElement) dragElement.getParent() == sideTrimBar;
			}

			return true;
		}

		return false;
