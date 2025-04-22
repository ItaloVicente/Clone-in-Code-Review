
		impExpHandlerContext = context.createChild();
		impExpHandlerContext.set(PerspectiveRegistry.class, this);
		ContextInjectionFactory.make(ImportExportPespectiveHandler.class, impExpHandlerContext);
	}

	public void addPerspective(MPerspective perspective) {
		application.getSnippets().add(perspective);
		createDescriptor(perspective);
	}

	private void createDescriptor(MPerspective perspective) {
		String label = perspective.getLocalizedLabel();
		String originalId = getOriginalId(perspective.getElementId());
		PerspectiveDescriptor originalDescriptor = descriptors.get(originalId);
		String id = perspective.getElementId();
		PerspectiveDescriptor newDescriptor = new PerspectiveDescriptor(id, label, originalDescriptor);

		if (perspective.getIconURI() != null) {
			try {
				ImageDescriptor img = ImageDescriptor.createFromURL(new URI(perspective.getIconURI()).toURL());
				newDescriptor.setImageDescriptor(img);
			} catch (MalformedURLException | URISyntaxException e) {
				logger.warn(e, MessageFormat.format("Error on applying configured perspective icon: {0}", //$NON-NLS-1$
						perspective.getIconURI()));
			}
		}

		descriptors.put(id, newDescriptor);
