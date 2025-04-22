	/** @return name of the current reference. */
	public String getRefName() {
		return ref != null ? ref.getName() : block.name();
	}

	/** @return time of reflog entry, microseconds since the epoch. */
