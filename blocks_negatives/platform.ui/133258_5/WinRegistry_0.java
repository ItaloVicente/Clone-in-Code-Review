			method_setAccessible.invoke(METHOD_openKey, true);
			METHOD_closeKey = prefClass.getDeclaredMethod("closeKey", int.class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_closeKey, true);
			METHOD_WinRegQueryValueEx = prefClass.getDeclaredMethod("WindowsRegQueryValueEx", int.class, byte[].class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_WinRegQueryValueEx, true);
			METHOD_WinRegSetValueEx1 = prefClass.getDeclaredMethod("WindowsRegSetValueEx1", int.class, byte[].class, byte[].class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_WinRegSetValueEx1, true);
			METHOD_WinRegDeleteKey = prefClass.getDeclaredMethod("WindowsRegDeleteKey", int.class, byte[].class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_WinRegDeleteKey, true);
