		if (mperspective.getIconURI() != null) {
			try {
				ImageDescriptor img = ImageDescriptor.createFromURL(new URI(mperspective.getIconURI()).toURL());
				newDesc.setImageDescriptor(img);
			} catch (MalformedURLException | URISyntaxException e) {
				WorkbenchPlugin.log(MessageFormat.format("Error on applying configured perspective icon: {0}", //$NON-NLS-1$
						mperspective.getIconURI(), e));
			}
		}

