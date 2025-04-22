	private void resetSystemColors(Display display) {
		try {
			Method initializeSystemColors = Display.class.getDeclaredMethod("initializeSystemColors"); //$NON-NLS-1$
			initializeSystemColors.setAccessible(true);
			initializeSystemColors.invoke(display);
		} catch (Exception ex) {
		}
	}

