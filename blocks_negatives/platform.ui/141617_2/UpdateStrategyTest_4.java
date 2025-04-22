
		for (int i = 0; i < boxedNumberTypes.length; i++) {
			Class<?> boxedType = boxedNumberTypes[i];

			if (!boxedType.equals(toType)
					&& !boxedType.equals(toCounterPrimitiveType)) {
