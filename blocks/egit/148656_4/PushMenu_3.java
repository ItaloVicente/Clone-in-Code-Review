				CommandContributionItemParameter params = new CommandContributionItemParameter(
						this.serviceLocator, getClass().getName(),
						ActionCommands.PUSH_BRANCH_ACTION,
						CommandContributionItem.STYLE_PUSH);
				params.label = menuLabel;
				CommandContributionItem item = new CommandContributionItem(
						params);
				return new IContributionItem[] { item };
