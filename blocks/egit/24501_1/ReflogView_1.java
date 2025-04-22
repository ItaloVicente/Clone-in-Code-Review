	private CommandContributionItem getCommandContributionItem(
			String commandId, String menuLabel, Map<String, String> parameters) {
		CommandContributionItemParameter parameter = new CommandContributionItemParameter(
				this.getSite(), commandId, commandId,
				CommandContributionItem.STYLE_PUSH);
		parameter.label = menuLabel;
		parameter.parameters = parameters;
		return new CommandContributionItem(parameter);
	}

