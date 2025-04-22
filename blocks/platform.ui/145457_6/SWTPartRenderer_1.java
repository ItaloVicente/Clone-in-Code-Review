	private String updateIconUri(MPart part) {
		MPartDescriptor desc = modelService.getPartDescriptor(part.getElementId());
		String iconURI = desc != null && desc.getIconURI() != null ? desc.getIconURI() : part.getIconURI();
		part.getTransientData().put(ICON_URI_FOR_PART, iconURI);
		return iconURI;
	}

