			for (IContributionItem contributionItem : getItems()) {
				if (clean.contains(contributionItem)) {
					continue;
				}
				contributionItem.update();
			}

