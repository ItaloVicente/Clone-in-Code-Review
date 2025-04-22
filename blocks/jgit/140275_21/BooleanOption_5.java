		return value ? BooleanOption.DEFAULT_TRUE : BooleanOption.DEFAULT_FALSE;
	}

	public static BooleanOption from(Optional<Boolean> orig) {
		if (!orig.isPresent())
			return DEFAULT_FALSE;
		return toConfigured(orig.get().booleanValue());
