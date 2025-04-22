				try {
					String ref = repository.getFullBranch();
					String menuLabel = UIText.PushMenu_PushHEAD;
					if (ref != null && ref.startsWith(Constants.R_HEADS)) {
						menuLabel = NLS.bind(UIText.PushMenu_PushBranch,
								Repository.shortenRefName(ref));
					}
					CommandContributionItemParameter params = new CommandContributionItemParameter(
							this.serviceLocator, getClass().getName(),
							ActionCommands.PUSH_BRANCH_ACTION,
							CommandContributionItem.STYLE_PUSH);
					params.label = menuLabel;
					CommandContributionItem item = new CommandContributionItem(
							params);
					res.add(item);
				} catch (IOException ex) {
					Activator.handleError(ex.getLocalizedMessage(), ex, false);
