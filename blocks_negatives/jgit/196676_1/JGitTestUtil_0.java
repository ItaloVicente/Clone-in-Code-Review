	/**
	 * Get name of current test by inspecting stack trace
	 *
	 * @return the name
	 */
	public static String getName() {
		GatherStackTrace stack;
		try {
			throw new GatherStackTrace();
		} catch (GatherStackTrace wanted) {
			stack = wanted;
		}

		try {
			for (StackTraceElement stackTrace : stack.getStackTrace()) {
				String className = stackTrace.getClassName();
				String methodName = stackTrace.getMethodName();
				Method method;
				try {
							.getMethod(methodName, (Class[]) null);
				} catch (NoSuchMethodException e) {
					continue;
				}

				Test annotation = method.getAnnotation(Test.class);
				if (annotation != null)
					return methodName;
			}
		} catch (ClassNotFoundException shouldNeverOccur) {
		}

		throw new AssertionError("Cannot determine name of current test");
	}

	@SuppressWarnings("serial")
	private static class GatherStackTrace extends Exception {
	}

