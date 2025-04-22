		if (command == null) {
			return;
		}
		String commandId = command.getElementId();
		if (handler.getObject() == null) {
			IContributionFactory contributionFactory = context.get(IContributionFactory.class);
			handler.setObject(contributionFactory.create(handler.getContributionURI(), context));
