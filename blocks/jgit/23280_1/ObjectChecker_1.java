
	private String normalize(byte[] raw
		String n = RawParseUtils.decode(raw
		return macosx ? Normalizer.normalize(n) : n;
	}

	private static class Normalizer {
		private static final Method normalize;
		private static final Object nfc;
		static {
			Method method;
			Object formNfc;
			try {
					.getMethod("normalize"
			} catch (ClassNotFoundException e) {
				method = null;
				formNfc = null;
			} catch (NoSuchFieldException e) {
				method = null;
				formNfc = null;
			} catch (NoSuchMethodException e) {
				method = null;
				formNfc = null;
			} catch (SecurityException e) {
				method = null;
				formNfc = null;
			} catch (IllegalArgumentException e) {
				method = null;
				formNfc = null;
			} catch (IllegalAccessException e) {
				method = null;
				formNfc = null;
			}
			normalize = method;
			nfc = formNfc;
		}

		static String normalize(String in) {
			if (normalize == null)
				return in;
			try {
				return (String) normalize.invoke(null
			} catch (IllegalAccessException e) {
				return in;
			} catch (InvocationTargetException e) {
				if (e.getCause() instanceof RuntimeException)
					throw (RuntimeException) e.getCause();
				if (e.getCause() instanceof Error)
					throw (Error) e.getCause();
				return in;
			}
		}
	}
