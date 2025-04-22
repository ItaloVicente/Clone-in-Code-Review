		if (handlerService == null)
			return;

		Repository repository = SelectionUtils
				.getRepository(handlerService.getCurrentState());
		if (repository != null)
