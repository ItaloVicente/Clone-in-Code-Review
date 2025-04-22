		return null;
	}

	private ImageDescriptor getIconDescriptor(MUILabel item) {
		String iconURI = item.getIconURI();
		if (iconURI != null && iconURI.length() > 0) {
			return resUtils.imageDescriptorFromURI(URI.createURI(iconURI));
		}
		return null;
