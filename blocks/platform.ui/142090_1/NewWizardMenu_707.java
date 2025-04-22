		list.add(new Separator());
		if (!shortCuts.isEmpty()) {
			list.addAll(shortCuts);
			list.add(new Separator());
		}
		if (hasExamples()) {
			list.add(new ActionContributionItem(newExampleAction));
			list.add(new Separator());
		}
		list.add(new ActionContributionItem(getShowDialogAction()));
	}
