		resSlot = next(resSlot);
	}

	private int next(int slot) {
		if (++slot == window.length)
			return 0;
		return slot;
	}

	private int prior(int slot) {
		if (slot == 0)
			return window.length - 1;
		return slot - 1;
