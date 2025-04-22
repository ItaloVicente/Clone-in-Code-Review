		if (element instanceof MPartDescriptor) {
			String iconURI = ((MPartDescriptor) element).getIconURI();
			if (iconURI != null && iconURI.length() > 0) {
				Image image = imageMap.get(iconURI);
				if (image == null) {
					ISWTResourceUtilities resUtils = (ISWTResourceUtilities) context
							.get(IResourceUtilities.class.getName());
					image = resUtils.imageDescriptorFromURI(URI.createURI(iconURI)).createImage();
					imageMap.put(iconURI, image);
				}
				return image;
