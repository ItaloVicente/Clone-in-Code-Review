
	private <T> T readField(Class clazz, String filedName, Class<T> type, Object instance) {
		try {
			Field field = clazz.getDeclaredField(filedName);
			field.setAccessible(true);
			return type.cast(field.get(instance));
		} catch (Exception e) {
			fail("Failure in reading " + clazz.getName() + filedName, e);
		}
		return null;
	}
