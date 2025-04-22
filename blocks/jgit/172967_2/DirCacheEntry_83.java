	public void setStage(int stage) {
		if ((stage & ~0x3) != 0) {
			throw new IllegalArgumentException(
					"Invalid stage
		}
		byte flags = info[infoOffset + P_FLAGS];
		info[infoOffset + P_FLAGS] = (byte) ((flags & 0xCF) | (stage << 4));
	}

