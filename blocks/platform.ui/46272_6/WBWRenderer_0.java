	void installSaveHandler(final MApplication application) {
		final PartServiceSaveHandler saveHandler = new PartServiceSaveHandler() {
			@Override
			public Save promptToSave(MPart dirtyPart) {
				Shell shell = (Shell) dirtyPart.getContext().get(IServiceConstants.ACTIVE_SHELL);
				Object[] elements = promptForSave(shell, Collections.singleton(dirtyPart));
				if (elements == null) {
					return Save.CANCEL;
				}
				return elements.length == 0 ? Save.NO : Save.YES;
			}

			@Override
			public Save[] promptToSave(Collection<MPart> dirtyParts) {
				List<MPart> parts = new ArrayList<>(dirtyParts);
				Shell shell = (Shell) application.getContext().get(IServiceConstants.ACTIVE_SHELL);
				Save[] response = new Save[dirtyParts.size()];
				Object[] elements = promptForSave(shell, parts);
				if (elements == null) {
					Arrays.fill(response, Save.CANCEL);
				} else {
					Arrays.fill(response, Save.NO);
					for (int i = 0; i < elements.length; i++) {
						response[parts.indexOf(elements[i])] = Save.YES;
					}
				}
				return response;
			}
		};
		saveHandler.logger = logger;
		application.getContext().set(ISaveHandler.class, saveHandler);
	}

	void installCloseHandler(MApplication application) {
		IEclipseContext context = application.getContext();
		context.set(IWindowCloseHandler.class, new IWindowCloseHandler() {

			@Override
			public boolean close(MWindow window) {
				if (window.getParent() == null) {
					return closeDetachedWindow(window);
				}

				EPartService partService = window.getContext().get(EPartService.class);
				return partService.saveAll(true);
			}
		});
