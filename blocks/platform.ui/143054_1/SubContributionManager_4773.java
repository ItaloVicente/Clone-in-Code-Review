		mapItemToWrapper.clear();
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (mapItemToWrapper.size() > 0) {
			Iterator<SubContributionItem> it = mapItemToWrapper.values().iterator();
			while (it.hasNext()) {
				IContributionItem item = it.next();
				item.setVisible(visible);
			}
			parentMgr.markDirty();
		}
	}

	protected SubContributionItem wrap(IContributionItem item) {
		return new SubContributionItem(item);
	}

	protected IContributionItem unwrap(IContributionItem item) {
		if (item instanceof SubContributionItem) {
			return ((SubContributionItem) item).getInnerItem();
		}

		return item;
	}
