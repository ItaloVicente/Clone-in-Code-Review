			int result;
			if (method_uses_long) {
				long parentHandle = (long) METHOD_openKey.invoke(userRoot, toByteArray(parent), KEY_DELETE, KEY_DELETE);
				result = (Integer) METHOD_WinRegDeleteKey.invoke(null, parentHandle, toByteArray(child));
				METHOD_closeKey.invoke(userRoot, parentHandle);
			} else {
				int parentHandle = (int) METHOD_openKey.invoke(userRoot, toByteArray(parent), KEY_DELETE, KEY_DELETE);
				result = (Integer) METHOD_WinRegDeleteKey.invoke(null, parentHandle, toByteArray(child));
				METHOD_closeKey.invoke(userRoot, parentHandle);
			}
