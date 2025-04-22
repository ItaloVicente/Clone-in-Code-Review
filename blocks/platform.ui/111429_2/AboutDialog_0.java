		if (commandService.getCommand(COPY_BUILD_ID_COMMAND).isDefined()) {
			textManager.add(new CommandContributionItem(new CommandContributionItemParameter(serviceLocator, null,
					COPY_BUILD_ID_COMMAND, CommandContributionItem.STYLE_PUSH)));
		}
		textManager.add(new CommandContributionItem(new CommandContributionItemParameter(serviceLocator, null,
				IWorkbenchCommandConstants.EDIT_SELECT_ALL,
