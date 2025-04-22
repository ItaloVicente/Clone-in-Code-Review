		ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(
				extendingPluginId, icon);

		if (image != null) {
			registry.setNatureImage(natureId, image);
		}

