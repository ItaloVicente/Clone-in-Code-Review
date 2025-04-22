	boolean isDoNotDelta() {
		return (flags & DO_NOT_DELTA) != 0;
	}

	void setDoNotDelta(boolean noDelta) {
		if (noDelta)
			flags |= DO_NOT_DELTA;
		else
			flags &= ~DO_NOT_DELTA;
	}

