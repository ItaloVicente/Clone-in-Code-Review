			int result;
			if (method_uses_long) {
				long handle = (long) METHOD_openKey.invoke(user, toByteArray(key), KEY_SET, KEY_SET);
				result = (Integer) METHOD_WinRegSetValueEx1.invoke(null, handle, toByteArray(attribute),
						toByteArray(value));
				METHOD_closeKey.invoke(systemRoot, handle);
			} else {
				int handle = (int) METHOD_openKey.invoke(user, toByteArray(key), KEY_SET, KEY_SET);
				result = (Integer) METHOD_WinRegSetValueEx1.invoke(null, handle, toByteArray(attribute),
						toByteArray(value));
				METHOD_closeKey.invoke(systemRoot, handle);

			}
