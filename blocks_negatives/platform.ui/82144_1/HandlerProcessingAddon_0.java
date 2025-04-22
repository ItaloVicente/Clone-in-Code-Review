		if (command != null) {
			String commandId = command.getElementId();
			if (handler.getObject() == null) {
				IContributionFactory contributionFactory = (IContributionFactory) context
						.get(IContributionFactory.class.getName());
				handler.setObject(contributionFactory.create(handler.getContributionURI(), context));
			}
			EHandlerService handlerService = (EHandlerService) context.get(EHandlerService.class
					.getName());
			handlerService.activateHandler(commandId, handler.getObject());
