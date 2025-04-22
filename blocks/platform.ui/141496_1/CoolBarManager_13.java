		this.contextMenuManager = (MenuManager) contextMenuManager;
		if (coolBar != null) {
			coolBar.setMenu(getContextMenuControl());
		}
	}

	public void setItems(IContributionItem[] newItems) {
		if (coolBar != null) {
			CoolItem[] coolItems = coolBar.getItems();
			for (CoolItem coolItem : coolItems) {
				dispose(coolItem);
			}
		}
		internalSetItems(newItems);
		update(true);
	}

	@Override
