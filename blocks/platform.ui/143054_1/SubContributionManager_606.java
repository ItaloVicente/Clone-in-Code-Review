		SubContributionItem wrap = wrap(item);
		wrap.setVisible(visible);
		parentMgr.appendToGroup(groupName, wrap);
		itemAdded(item, wrap);
	}

	public void disposeManager() {
		Iterator<SubContributionItem> it = mapItemToWrapper.values().iterator();
		while (it.hasNext()) {
			IContributionItem item = it.next();
			item.dispose();
		}
		removeAll();
	}

	@Override
