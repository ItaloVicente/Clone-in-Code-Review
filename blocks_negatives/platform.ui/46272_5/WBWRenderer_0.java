		final PartServiceSaveHandler saveHandler = new PartServiceSaveHandler() {
			@Override
			public Save promptToSave(MPart dirtyPart) {
				Shell shell = (Shell) context
						.get(IServiceConstants.ACTIVE_SHELL);
				Object[] elements = promptForSave(shell,
						Collections.singleton(dirtyPart));
				if (elements == null) {
					return Save.CANCEL;
				}
				return elements.length == 0 ? Save.NO : Save.YES;
			}

			@Override
			public Save[] promptToSave(Collection<MPart> dirtyParts) {
				List<MPart> parts = new ArrayList<>(dirtyParts);
				Shell shell = (Shell) context
						.get(IServiceConstants.ACTIVE_SHELL);
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
		localContext.set(ISaveHandler.class, saveHandler);
