
	public static Format getDefaultFormat() {
		return GET_INSTANCE.get();
	}

	public static Format getDefaultBigDecimalFormat() {
		Format format = GET_NUMBER_INSTANCE.get();
		if (format instanceof DecimalFormat) {
			((DecimalFormat) format).setParseBigDecimal(true);
		}
		return format;
	}

	public static Format getDefaultNumberFormat() {
		return GET_NUMBER_INSTANCE.get();
	}

	public static Format getDefaultIntegerFormat() {
		return GET_INTEGER_INSTANCE.get();
	}

	public static Format getDefaultIntegerBigDecimalFormat() {
		Format format = GET_INTEGER_INSTANCE.get();
		if (format instanceof DecimalFormat) {
			((DecimalFormat) format).setParseBigDecimal(true);
		}
		return format;
	}

	private static Supplier<Format> findMethod(Supplier<Format> javaTextMethod, String methodName) {
		try {
			Method method = Class.forName("com.ibm.icu.text.NumberFormat").getMethod(methodName); //$NON-NLS-1$
			return () -> {
				try {
					return (Format) method.invoke(null);
				} catch (ReflectiveOperationException e) {
					throw new RuntimeException(e); // Should never happen
				}
			};
		} catch (ClassNotFoundException | SecurityException e) {
			return javaTextMethod;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e); // Should never happen
		}
	}
