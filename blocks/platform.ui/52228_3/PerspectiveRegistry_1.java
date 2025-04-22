					PerspectiveDescriptor newDescriptor = new PerspectiveDescriptor(id, label, originalDescriptor);

					if (perspective.getIconURI() != null) {
						try {
							ImageDescriptor img = ImageDescriptor
									.createFromURL(new URI(perspective.getIconURI()).toURL());
							newDescriptor.setImageDescriptor(img);
						} catch (MalformedURLException | URISyntaxException e) {
							logger.warn(e, MessageFormat.format("Error on applying configured perspective icon: {0}", //$NON-NLS-1$
									perspective.getIconURI()));
						}
					}

