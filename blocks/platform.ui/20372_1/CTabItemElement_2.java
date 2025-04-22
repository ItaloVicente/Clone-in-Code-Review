	@Override
	public void reset() {
		super.reset();
		CTabItem item = getItem();
		item.setImage(null);
		item.setFont(null); // in such case the parent's font will be taken
	}

