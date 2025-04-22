
		protected Object executeMethod(Method method, Object... params) {
			Object value = null;
			if (method != null) {
				boolean accessible = method.isAccessible();
				try {
					method.setAccessible(true);
					value = method.invoke(instance, params);
				} catch (Exception exc) {
				} finally {
					method.setAccessible(accessible);
				}
			}
			return value;
		}

		protected Method getMethod(String name, Class<?>... params) {
			Class<?> cls = instance.getClass();
			while (!cls.equals(Object.class)) {
				try {
					return cls.getDeclaredMethod(name, params);
				} catch (Exception exc) {
					cls = cls.getSuperclass();
				}
			}
			return null;
		}
