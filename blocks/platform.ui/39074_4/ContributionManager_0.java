		String id = item.getId();
		if (id != null) {
			boolean found = false;
			for (IContributionItem contItem : contributions) {
				if (!item.isSeparator() && id.equals(contItem.getId())) {
					if (found) {
						contributions.remove(item);
						item.setParent(null);
						return;
					}
					found = true;
				}
			}
		}

