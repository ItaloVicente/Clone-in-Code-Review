	private boolean isCommandAvailable(final String id) {
		return commandService != null
				&& commandService.getCommand(id).isDefined();
	}

	protected void invokeCloseCommand(final String id,
			final MElementContainer<MUIElement> parent, final Menu menu) {
		MPart part = (MPart) menu.getData(STACK_SELECTED_PART);

		if (parent.getSelectedElement() != part) {
			parent.setSelectedElement(part);
		}

		final IEclipseContext eContext = modelService
				.getContainingContext(part);
		final ParameterizedCommand pc = commandService.createCommand(id,
				Collections.emptyMap());
		final EHandlerService handlerService = eContext
				.get(EHandlerService.class);
		handlerService.executeHandler(pc);
	}

