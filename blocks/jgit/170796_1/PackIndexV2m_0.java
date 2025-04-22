	private static final Unsafe UNSAFE;

	static {
		try {
			final Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			UNSAFE = (Unsafe)f.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

