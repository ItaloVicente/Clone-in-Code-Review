	public static ISaveablePart getSaveable(Object o) {
		return (ISaveablePart) Util.getAdapter(o, ISaveablePart.class);
	}

	public static boolean isSaveable(Object o) {
		return getSaveable(o) != null;
	}

	public static ISaveablePart2 getSaveable2(Object o) {
		ISaveablePart saveable = getSaveable(o);
		if (saveable instanceof ISaveablePart2) {
			return (ISaveablePart2) saveable;
		}
		return (ISaveablePart2) Util.getAdapter(o, ISaveablePart2.class);
	}

	public static boolean isSaveable2(Object o) {
		return getSaveable2(o) != null;
	}

