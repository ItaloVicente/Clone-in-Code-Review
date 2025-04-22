		if (!valuesToKeys.containsKey(value))
			return Collections.EMPTY_SET;
		Object elementOrSet = valuesToKeys.get(value);
		if (elementOrSet instanceof Set)
			return Collections.unmodifiableSet((Set) elementOrSet);
		return Collections.singleton(elementOrSet);
