	Collection<Pack> getPacks() {
		return getPacks(false);
	}

	public void refreshPacks() {
		Collection<Pack> n = getPacks(true);
		PackList o = this.packList.get();
		this.packList.compareAndSet(
				o
	}

