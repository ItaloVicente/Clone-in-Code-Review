	private static final Preferences USER_ROOT = Preferences.userRoot();
	private static final int KEY_READ = 1;
	private static final int KEY_SET = 2;
	private static final int KEY_DELETE = 0x10000;

	private static Method METHOD_stringToByteArray;
	private static Method METHOD_WinRegQueryValueEx;
	private static Method METHOD_toJavaValueString;
	private static Method METHOD_openKey;
	private static Method METHOD_closeKey;
	private static Method METHOD_WinRegSetValueEx1;
	private static Method METHOD_WinRegDeleteKey;

	static {
		try {
			Class<?> prefClass = USER_ROOT.getClass();
			METHOD_stringToByteArray = prefClass.getDeclaredMethod("stringToByteArray", String.class); //$NON-NLS-1$
			METHOD_toJavaValueString = prefClass.getDeclaredMethod("toJavaValueString", byte[].class); //$NON-NLS-1$
			METHOD_openKey = prefClass.getDeclaredMethod("openKey", byte[].class, int.class, int.class); //$NON-NLS-1$
			Class<?> parameterType = int.class;
			try {
				METHOD_closeKey = prefClass.getDeclaredMethod("closeKey", parameterType); //$NON-NLS-1$
			} catch (NoSuchMethodException e1) {
				parameterType = long.class;
				METHOD_closeKey = prefClass.getDeclaredMethod("closeKey", parameterType); //$NON-NLS-1$
			}
			METHOD_WinRegQueryValueEx = prefClass.getDeclaredMethod("WindowsRegQueryValueEx", parameterType, //$NON-NLS-1$
					byte[].class);
			METHOD_WinRegSetValueEx1 = prefClass.getDeclaredMethod("WindowsRegSetValueEx1", parameterType, byte[].class, //$NON-NLS-1$
					byte[].class);
			METHOD_WinRegDeleteKey = prefClass.getDeclaredMethod("WindowsRegDeleteKey", parameterType, byte[].class); //$NON-NLS-1$

			AccessibleObject[] allMethods = new AccessibleObject[] { METHOD_stringToByteArray, METHOD_toJavaValueString,
					METHOD_openKey, METHOD_closeKey, METHOD_WinRegQueryValueEx, METHOD_WinRegSetValueEx1,
					METHOD_WinRegDeleteKey };
			AccessibleObject.setAccessible(allMethods, true);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
