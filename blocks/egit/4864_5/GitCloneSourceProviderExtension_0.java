		try {
			int myIndex = index;
			String label = config[myIndex].getAttribute("label"); //$NON-NLS-1$
			boolean hasFixLocation = Boolean.valueOf(
					config[myIndex].getAttribute("hasFixLocation")).booleanValue(); //$NON-NLS-1$


			String iconPath = config[myIndex].getAttribute("icon"); //$NON-NLS-1$
			ImageDescriptor icon = null;
			if (iconPath != null) {
				Bundle declaringBundle = Platform.getBundle(config[myIndex].getDeclaringExtension().getNamespaceIdentifier());
				icon = ImageDescriptor.createFromURL(declaringBundle.getResource(iconPath));
			}
