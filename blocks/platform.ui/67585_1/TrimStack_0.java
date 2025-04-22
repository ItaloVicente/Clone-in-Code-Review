		if (result == null && element instanceof MPart) {
			MPart part = (MPart) element;
			if (ModelServiceImpl.COMPATIBILITY_VIEW_URI.equals(part.getContributionURI()) && part.getIconURI() == null) {
				result = ImageDescriptor.getMissingImageDescriptor().createImage();
				element.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY, result);
			}
		}

