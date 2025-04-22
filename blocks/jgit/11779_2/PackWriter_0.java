	public boolean isIndexDisabled() {
		return indexDisabled || !cachedPacks.isEmpty();
	}

	public void setIndexDisabled(boolean noIndex) {
		this.indexDisabled = noIndex;
	}

