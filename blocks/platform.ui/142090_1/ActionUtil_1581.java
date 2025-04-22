			if (item instanceof SubContributionItem)
				item = ((SubContributionItem) item).getInnerItem();
			if (item instanceof ActionContributionItem) {
				IAction action = ((ActionContributionItem) item).getAction();
				if (label.equals(action.getText())) {
					return action;
				}
			}
		}
		return null;
	}
