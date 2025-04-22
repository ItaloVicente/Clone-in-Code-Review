		if (oldItems != null) {
			for (IContributionItem oldItem : oldItems) {
				oldItem.dispose();
			}
			oldItems = null;
		}
	}
