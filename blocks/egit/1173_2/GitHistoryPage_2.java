		if (checkoutItem == null) {
			CommandContributionItemParameter p = new CommandContributionItemParameter(
					getSite(), SharedCommands.CHECKOUT,
					SharedCommands.CHECKOUT, CommandContributionItem.STYLE_PUSH);
			checkoutItem = new CommandContributionItem(p);
		}

