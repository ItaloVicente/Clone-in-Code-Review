
		imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(configElement.getContributor().getName(), strIcon);

		if (imageDescriptor == null) {
			return;
		}

		Image image = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
		titleImage = image;
