	boolean isAttemptDelta() {
		return (flags & ATTEMPT_DELTA_MASK) != ATTEMPT_DELTA_MASK;
	}

	boolean isDeltaAttempted() {
		return (flags & DELTA_ATTEMPTED) != 0;
	}

	void setDeltaAttempted(boolean deltaAttempted) {
		if (deltaAttempted)
			flags |= DELTA_ATTEMPTED;
		else
			flags &= DELTA_ATTEMPTED;
	}

