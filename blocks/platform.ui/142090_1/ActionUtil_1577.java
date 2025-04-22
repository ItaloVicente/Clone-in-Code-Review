			if (item instanceof SubContributionItem)
				item = ((SubContributionItem) item).getInnerItem();
			if (item instanceof ActionContributionItem) {
				IAction action = ((ActionContributionItem) item).getAction();
				if (label.equals(action.getText())) {
					action.run();
					return;
				}
			}
		}
		Assert.fail("Unable to find action: " + label);
	}
