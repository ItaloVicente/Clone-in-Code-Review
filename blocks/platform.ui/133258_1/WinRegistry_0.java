			try {
				METHOD_closeKey = prefClass.getDeclaredMethod("closeKey", int.class); //$NON-NLS-1$
			} catch (NoSuchMethodException e1) {
				METHOD_closeKey = prefClass.getDeclaredMethod("closeKey", long.class); //$NON-NLS-1$
				method_uses_long = true;
			}
			if (method_uses_long == false) {
				METHOD_WinRegQueryValueEx = prefClass.getDeclaredMethod("WindowsRegQueryValueEx", int.class, //$NON-NLS-1$
						byte[].class);
				METHOD_WinRegSetValueEx1 = prefClass.getDeclaredMethod("WindowsRegSetValueEx1", int.class, byte[].class, //$NON-NLS-1$
						byte[].class);
				METHOD_WinRegDeleteKey = prefClass.getDeclaredMethod("WindowsRegDeleteKey", int.class, byte[].class); //$NON-NLS-1$
			} else {
				METHOD_WinRegQueryValueEx = prefClass.getDeclaredMethod("WindowsRegQueryValueEx", long.class, //$NON-NLS-1$
						byte[].class);
				METHOD_WinRegSetValueEx1 = prefClass.getDeclaredMethod("WindowsRegSetValueEx1", long.class, //$NON-NLS-1$
						byte[].class, byte[].class);
				METHOD_WinRegDeleteKey = prefClass.getDeclaredMethod("WindowsRegDeleteKey", long.class, byte[].class); //$NON-NLS-1$
			}
			method_setAccessible.invoke(METHOD_stringToByteArray, true);
			method_setAccessible.invoke(METHOD_toJavaValueString, true);
