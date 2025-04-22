	public int hashCode() {
		if (hashCode == HASH_INITIAL) {
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(activityId);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(categoryId);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(sourceId);
			if (hashCode == HASH_INITIAL) {
