		if (!resolveAlways) {
			List<MPart> partList = findElements(element, null, MPart.class, null);
			for (MPart part : partList) {
				if (COMPATIBILITY_VIEW_URI.equals(part.getContributionURI()) && part.getIconURI() == null) {
					part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY,
							ImageDescriptor.getMissingImageDescriptor().createImage());
				}
			}
		}
		for (MPlaceholder ph : nullRefList) {
			replacePlaceholder(ph);
		}

		return;
