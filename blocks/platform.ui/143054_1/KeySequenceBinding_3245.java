		if (!hashCodeComputed) {
			hashCode = HASH_INITIAL;
			hashCode = hashCode * HASH_FACTOR + Objects.hashCode(keySequence);
			hashCode = hashCode * HASH_FACTOR + Integer.hashCode(match);
			hashCodeComputed = true;
		}
