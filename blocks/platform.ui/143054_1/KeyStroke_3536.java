		if (!hashCodeComputed) {
			hashCode = HASH_INITIAL;
			hashCode = hashCode * HASH_FACTOR + modifierKeys.hashCode();
			hashCode = hashCode * HASH_FACTOR + Objects.hashCode(naturalKey);
			hashCodeComputed = true;
		}

		return hashCode;
	}

	public boolean isComplete() {
		return naturalKey != null;
	}

	@Override
