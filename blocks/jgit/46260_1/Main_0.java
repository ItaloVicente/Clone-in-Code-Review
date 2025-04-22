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
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		} catch (SecurityException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		} catch (IllegalAccessException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		} catch (InvocationTargetException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		}
	}

	private static void install(final String name)
			throws IllegalAccessException
			NoSuchMethodException
		try {
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException)
				throw (RuntimeException) e.getCause();
			if (e.getCause() instanceof Error)
				throw (Error) e.getCause();
			throw e;
		}
	}

