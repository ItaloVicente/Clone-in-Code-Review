		if (isLastEditorStack(containerElement) || containerElement instanceof MPerspective
				|| containerElement instanceof MPerspectiveStack)
			return;

		Boolean toBeRendered = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
		if (toBeRendered) {
			if (!container.isToBeRendered())
				container.setToBeRendered(true);
			if (!container.isVisible()
					&& !container.getTags().contains(IPresentationEngine.MINIMIZED))
				container.setVisible(true);
		} else {
			if (container.getTags().contains(IPresentationEngine.NO_AUTO_COLLAPSE)) {
