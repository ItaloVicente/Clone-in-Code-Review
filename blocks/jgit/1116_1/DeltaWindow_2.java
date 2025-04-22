		resSlot = next(resSlot);
	}

	private int next(int slot) {
		if (++slot == window.length)
			return 0;
		return slot;
