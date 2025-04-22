	public static BooleanOption from(Optional<Boolean> orig) {
		if (!orig.isPresent())
			return NOT_DEFINED_FALSE;
		return defined(orig.get().booleanValue());
	}

