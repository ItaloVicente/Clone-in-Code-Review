		List<MPart> parts = modelService.findElements(currentPerspective, null, MPart.class, null);

		for (MPart part : parts) {
			if (!partService.isPartOrPlaceholderInPerspective(part.getElementId(), currentPerspective)) {
				continue;
			}

			if (part.getTags().contains("Editor")) { //$NON-NLS-1$
