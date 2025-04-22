	public static ISaveablePart3 getSaveable3(Object o) {
		ISaveablePart saveable = getSaveable(o);
		if (saveable instanceof ISaveablePart3) {
			return (ISaveablePart3) saveable;
		}
		return Adapters.adapt(o, ISaveablePart3.class);
	}

	public static boolean isSaveable3(Object o) {
		return getSaveable3(o) != null;
	}
