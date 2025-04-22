	private boolean equalIdAndMode(ObjectId id1
			FileMode mode2) {
		if (!mode1.equals(mode2))
			return false;
		return id1 != null ? id1.equals(id2) : id2 == null;
	}

