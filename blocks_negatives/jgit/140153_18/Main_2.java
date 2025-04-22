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
