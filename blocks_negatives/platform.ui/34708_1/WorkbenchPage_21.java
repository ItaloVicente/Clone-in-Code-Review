		modelPerspective.setLabel(perspective.getLabel());

		ImageDescriptor imageDescriptor = perspective.getImageDescriptor();
		if (imageDescriptor != null) {
			String imageURL = MenuHelper.getImageUrl(imageDescriptor);
			modelPerspective.setIconURI(imageURL);
		}
