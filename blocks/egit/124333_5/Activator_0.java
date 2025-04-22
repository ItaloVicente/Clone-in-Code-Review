	private void registerBuiltinLFS() {
		Class<?> lfs;
		try {
			lfs = Class.forName("org.eclipse.jgit.lfs.BuiltinLFS"); //$NON-NLS-1$
			if (lfs != null) {
				lfs.getMethod("register").invoke(null); //$NON-NLS-1$
			}
		} catch (ClassNotFoundException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e1) {
			logWarning(CoreText.Activator_noBuiltinLfsSupportDetected, e1);
		}
	}

