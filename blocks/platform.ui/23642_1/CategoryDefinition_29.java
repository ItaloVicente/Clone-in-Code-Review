		return Util.equals(sourceId, castedObject.sourceId);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSourceId() {
		return sourceId;
	}

	public int hashCode() {
		if (hashCode == HASH_INITIAL) {
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(id);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(name);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(sourceId);
			if (hashCode == HASH_INITIAL) {
