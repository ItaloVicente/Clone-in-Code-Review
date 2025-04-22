
		imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(configElement.getContributor().getName(), strIcon);

		if (imageDescriptor == null) {
			return;
		}

		titleImage = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
