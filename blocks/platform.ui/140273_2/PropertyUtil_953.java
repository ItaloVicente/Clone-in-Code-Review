	private PropertyUtil() {
	}

	public static boolean isEqual(IPropertyMap map1, IPropertyMap map2) {
		Set map1Keys = map1.keySet();
		Set map2Keys = map2.keySet();

		if (!map1Keys.equals(map2Keys)) {
			return false;
		}

		for (Iterator iter = map1Keys.iterator(); iter.hasNext();) {
			String next = (String) iter.next();

			if (!map1.getValue(next, Object.class).equals(map2.getValue(next, Object.class))) {
				return false;
			}
		}

		return true;
	}

	public static void copy(IPropertyMap destination, IPropertyMap source) {
		Set keys = source.keySet();

		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			destination.setValue(key, source.getValue(key, Object.class));
		}
	}

	public static IPropertyMap union(IPropertyMap[] sources) {
		PropertyMapUnion result = new PropertyMapUnion();

		for (IPropertyMap map : sources) {
			result.addMap(map);
		}

		return result;
	}

	public static boolean get(IPropertyMap toRead, String propertyId, boolean defaultValue) {
		Boolean result = ((Boolean) toRead.getValue(propertyId, Boolean.class));

		if (result == null) {
			return defaultValue;
		}

		return result.booleanValue();
	}

	public static int get(IPropertyMap toRead, String propertyId, int defaultValue) {
		Integer result = ((Integer) toRead.getValue(propertyId, Integer.class));

		if (result == null) {
			return defaultValue;
		}

		return result.intValue();
	}
