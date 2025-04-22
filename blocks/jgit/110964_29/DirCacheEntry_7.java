	void setExtended(boolean extended) {
		if (extended) {
			info[infoOffset + P_FLAGS] |= EXTENDED;
		} else {
			info[infoOffset + P_FLAGS] &= ~EXTENDED;
		}
	}

