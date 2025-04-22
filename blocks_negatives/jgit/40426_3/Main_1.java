	private static boolean installConsole() {
		try {
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (NoClassDefFoundError e) {
			return false;
		} catch (UnsupportedClassVersionError e) {
			return false;

		} catch (IllegalArgumentException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole, e);
		} catch (SecurityException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole, e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole, e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole, e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole, e);
		}
	}

	private static void install(final String name)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, ClassNotFoundException {
		try {
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException)
				throw (RuntimeException) e.getCause();
			if (e.getCause() instanceof Error)
				throw (Error) e.getCause();
			throw e;
		}
	}

