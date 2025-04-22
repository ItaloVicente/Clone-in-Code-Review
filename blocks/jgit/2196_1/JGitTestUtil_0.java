	public static String getName() {
		Throwable throwable = new Throwable();
		for (StackTraceElement stackTrace : throwable.getStackTrace()) {
			String className = stackTrace.getClassName();
			String methodName = stackTrace.getMethodName();
			Method method;
			try {
				method = Class.forName(className).getMethod(methodName
						(Class[]) null);
			} catch (SecurityException e) {
				throw new Error(
						"Cannot determie name of test
						e);
			} catch (NoSuchMethodException e) {
				continue;
			} catch (ClassNotFoundException e) {
				throw new Error(
						"Cannot determie name of test
						e);
			}
			Test annotation = method.getAnnotation(Test.class);
			if (annotation != null)
				return methodName;
		}
		throw new Error(
				"Cannot determie name of test
	}

	public static void assertEquals(byte[] exp
		Assert.assertEquals(s(exp)
	}

	private static String s(byte[] raw) {
		return RawParseUtils.decode(raw);
	}

