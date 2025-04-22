	private static boolean installConsole() {
		try {
			install("org.eclipse.jgit.ui.console.ConsoleAuthenticator");
			install("org.eclipse.jgit.ui.console.ConsoleSshSessionFactory");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (NoClassDefFoundError e) {
			return false;
		} catch (UnsupportedClassVersionError e) {
			return false;

		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Cannot setup console"
		} catch (SecurityException e) {
			throw new RuntimeException("Cannot setup console"
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot setup console"
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot setup console"
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Cannot setup console"
		}
	}

	private static void install(final String name)
			throws IllegalAccessException
			NoSuchMethodException
		Class.forName(name).getMethod("install").invoke(null);
	}

