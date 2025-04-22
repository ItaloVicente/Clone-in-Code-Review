		this.maxBytes = reader.getOptions().getChunkLimit();
	}

	void setMaxBytes(int newMax) {
		maxBytes = Math.max(0
		if (0 < maxBytes)
			prune();
		else
			clear();
