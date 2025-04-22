		if (element instanceof MPartDescriptor) {
			String elementId = ((MPartDescriptor) element).getElementId();
			List<MPart> findElements = modelService.findElements(
					modelService.getActivePerspective(window), elementId, MPart.class, null);

			if (findElements.size() > 0) {
				MPart mPart = findElements.get(0);
				if (mPart.isVisible() && mPart.isToBeRendered()) {
