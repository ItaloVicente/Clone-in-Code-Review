		if (!hashCodeComputed) {
			hashCode = HASH_INITIAL;
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(keySequence);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(match);
			hashCodeComputed = true;
		}
