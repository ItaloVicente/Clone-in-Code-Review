		if (element instanceof MPartDescriptor) {
			String elementId = ((MPartDescriptor) element).getElementId();
			List<MPart> findElements = modelService.findElements(
					modelService.getActivePerspective(window), elementId, MPart.class, null);

			if (findElements.size() > 0) {
				MPart part = findElements.get(0);

				if (window.getSharedElements().contains(part)) {
					List<MPlaceholder> placeholders = modelService.findElements(
							modelService.getActivePerspective(window), elementId, MPlaceholder.class, null);
					for (MPlaceholder mPlaceholder : placeholders) {
						if (mPlaceholder.isVisible() && mPlaceholder.isToBeRendered()) {
							return dimmedForeground;
						}
					}
					return null;
				}
				if (part.isVisible() && part.isToBeRendered()) {
