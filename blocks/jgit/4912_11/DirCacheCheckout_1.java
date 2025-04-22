	private boolean equalIdAndMode(ObjectId id1
			FileMode mode2) {
		if (mode1.equals(mode2)) {
			if (id1 == null)
				return id2 == null;
			return (id1.equals(id2));
		}
		return false;
	}

