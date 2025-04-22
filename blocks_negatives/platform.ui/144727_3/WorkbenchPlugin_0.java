			try {
				Method method = Shell.class.getMethod("internal_new", Display.class, long.class); //$NON-NLS-1$

				splashShell = (Shell) method.invoke(null, display, Long.valueOf(splashHandle));
			} catch (NoSuchMethodException e2) {
			}
