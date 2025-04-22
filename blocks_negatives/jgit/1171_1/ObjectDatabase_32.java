	public final boolean hasObject(final AnyObjectId objectId) {
		return hasObjectImpl1(objectId) || hasObjectImpl2(objectId.name());
	}

	private final boolean hasObjectImpl1(final AnyObjectId objectId) {
		if (hasObject1(objectId)) {
			return true;
		}
		for (final ObjectDatabase alt : getAlternates()) {
			if (alt.hasObjectImpl1(objectId)) {
				return true;
			}
		}
		return tryAgain1() && hasObject1(objectId);
	}

	private final boolean hasObjectImpl2(final String objectId) {
		if (hasObject2(objectId)) {
			return true;
		}
		for (final ObjectDatabase alt : getAlternates()) {
			if (alt.hasObjectImpl2(objectId)) {
				return true;
			}
		}
		return false;
	}
