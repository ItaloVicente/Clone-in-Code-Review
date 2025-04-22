	final int getChainLength() {
		return getDeltaDepth();
	}

	final void setChainLength(int len) {
		setDeltaDepth(len);
	}

	final void clearChainLength() {
		flags &= NON_DELTA_MASK;
	}

