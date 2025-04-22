				if (!input.getRepository().isBare()) {
					if (hasMultipleRefNodes()) {
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CHECKOUT,
								UIText.GitHistoryPage_CheckoutMenuLabel2));
					} else {
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CHECKOUT,
								UIText.GitHistoryPage_CheckoutMenuLabel));
					}
