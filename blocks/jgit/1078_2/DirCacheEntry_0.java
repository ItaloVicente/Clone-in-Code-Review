	public boolean isUpdateNeeded() {
		return (info[infoOffset + P_FLAGS] & UPDATE_NEEDED) != 0;
	}

	public void setUpdateNeeded(boolean updateNeeded) {
		if (updateNeeded)
			info[infoOffset + P_FLAGS] |= UPDATE_NEEDED;
		else
			info[infoOffset + P_FLAGS] &= ~UPDATE_NEEDED;
	}

