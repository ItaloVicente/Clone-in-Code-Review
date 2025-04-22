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
