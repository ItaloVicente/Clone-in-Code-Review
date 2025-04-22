	private static final boolean isGTK2;

	static {
		boolean gtk2Value = false;
		try {
			@SuppressWarnings("rawtypes")
			Class osClass = Class.forName("org.eclipse.swt.internal.gtk.OS"); //$NON-NLS-1$
			Field gtk3Field = osClass.getField("GTK3"); //$NON-NLS-1$
			Object value = gtk3Field.get(null);
			gtk2Value = (Boolean.FALSE.equals(value));
		} catch (ClassNotFoundException e) {
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}

		isGTK2 = gtk2Value;
	}

