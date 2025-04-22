	private CommandContributionItem getCommandContributionItem(
			String commandId, String menuLabel) {
		CommandContributionItemParameter parameter = new CommandContributionItemParameter(
				getSite(), commandId, commandId,
				CommandContributionItem.STYLE_PUSH);
		parameter.label = menuLabel;
		return new CommandContributionItem(parameter);
	}

	private CommandContributionItem getCommandContributionItem(
			String commandId, String menuLabel, Map<String, String> parameters) {
		CommandContributionItemParameter parameter = new CommandContributionItemParameter(
				getSite(), commandId, commandId,
				CommandContributionItem.STYLE_PUSH);
		parameter.label = menuLabel;
		parameter.parameters = parameters;
		return new CommandContributionItem(parameter);
	}

