	private String updateIconUri(MPart part) {
		MPartDescriptor desc = modelService.getPartDescriptor(part.getElementId());
		String iconURI = part.getIconURI();
		if (iconURI == null && desc != null) {
			iconURI = desc.getIconURI();
		}
		part.getTransientData().put(ICON_URI_FOR_PART, iconURI);
		return iconURI;
	}

