			IContributionItem item;
			String actionText = action.getText();
			if (actionText == null || actionText.isEmpty()) {
				item = new Separator();
			} else {
				item = new ActionContributionItem(action);
			}
