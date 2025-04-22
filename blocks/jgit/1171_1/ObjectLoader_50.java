	public boolean isLarge() {
		try {
			getCachedBytes();
			return false;
		} catch (LargeObjectException tooBig) {
			return true;
		}
	}

