		return parentMgr.isEmpty();
	}

	public boolean isVisible() {
		return visible;
	}

	protected void itemAdded(IContributionItem item, SubContributionItem wrap) {
		item.setParent(this);
		mapItemToWrapper.put(item, wrap);
	}

	protected void itemRemoved(IContributionItem item) {
		mapItemToWrapper.remove(item);
		item.setParent(null);
	}

	@Deprecated
