	static public BooleanOption defined(boolean value) {
		return value ? BooleanOption.True : BooleanOption.False;
	}

	static public BooleanOption notDefined(boolean value) {
		return value ? BooleanOption.notDefinedTrue
				: BooleanOption.notDefinedFalse;
	}

