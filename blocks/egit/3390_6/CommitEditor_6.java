		refListenerHandle = Repository.getGlobalListenerList()
				.addRefsChangedListener(this);
	}

	private CommandContributionItem createCommandContributionItem(
			String commandId) {
		CommandContributionItemParameter parameter = new CommandContributionItemParameter(
				getSite(), commandId, commandId,
				CommandContributionItem.STYLE_PUSH);
		return new CommandContributionItem(parameter);
