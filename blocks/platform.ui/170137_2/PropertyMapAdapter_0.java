		return toCompare instanceof IPropertyMap && isEqual(this, (IPropertyMap) toCompare);
	}

	private static boolean isEqual(IPropertyMap map1, IPropertyMap map2) {
		Set<String> map1Keys = map1.keySet();
		Set<String> map2Keys = map2.keySet();

		if (!map1Keys.equals(map2Keys)) {
			return false;
		}

		for (String next : map1Keys) {
			if (!map1.getValue(next, Object.class).equals(map2.getValue(next, Object.class))) {
				return false;
			}
		}

		return true;
