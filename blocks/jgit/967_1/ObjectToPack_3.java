
	boolean isReuseAsIs() {
		return (flags & REUSE_AS_IS) != 0;
	}

	void setReuseAsIs() {
		flags |= REUSE_AS_IS;
	}

	void clearReuseAsIs() {
		flags &= ~REUSE_AS_IS;
	}

	int getFormat() {
		if (isReuseAsIs()) {
			if (isDeltaRepresentation())
				return StoredObjectRepresentation.PACK_DELTA;
			return StoredObjectRepresentation.PACK_WHOLE;
		}
		return StoredObjectRepresentation.FORMAT_OTHER;
	}

	int getWeight() {
		return getCRC();
	}

	void setWeight(int weight) {
		setCRC(weight);
	}

	public void select(StoredObjectRepresentation ref) {
	}
