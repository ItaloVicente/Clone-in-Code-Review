		if (hashCode == HASH_INITIAL) {
			hashCode = hashCode * HASH_FACTOR + Objects.hashCode(categoryActivityBindings);
			hashCode = hashCode * HASH_FACTOR + Boolean.hashCode(defined);
			hashCode = hashCode * HASH_FACTOR + Objects.hashCode(id);
			hashCode = hashCode * HASH_FACTOR + Objects.hashCode(name);
			if (hashCode == HASH_INITIAL) {
