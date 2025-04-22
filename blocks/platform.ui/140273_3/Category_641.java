		if (hashCode == HASH_INITIAL) {
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(categoryActivityBindings);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(defined);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(id);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(name);
			if (hashCode == HASH_INITIAL) {
