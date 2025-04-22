
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


		descriptors.put(id, newDescriptor);
