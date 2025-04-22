	public static ISaveablePart getSaveable(Object o) {
		if (o instanceof ISaveablePart) {
			return (ISaveablePart) o;
		}
		return Util.getAdapter(o, ISaveablePart.class);
	}

	public static boolean isSaveable(Object o) {
		return getSaveable(o) != null;
	}

	public static ISaveablePart2 getSaveable2(Object o) {
		ISaveablePart saveable = getSaveable(o);
		if (saveable instanceof ISaveablePart2) {
			return (ISaveablePart2) saveable;
		}
		return Util.getAdapter(o, ISaveablePart2.class);
	}

	public static boolean isSaveable2(Object o) {
		return getSaveable2(o) != null;
	}

