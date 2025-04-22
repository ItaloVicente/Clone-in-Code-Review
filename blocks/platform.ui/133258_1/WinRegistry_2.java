			byte[] valb;
			if (method_uses_long) {
				long handle = (long) METHOD_openKey.invoke(user, toByteArray(key), KEY_READ, KEY_READ);
				valb = (byte[]) METHOD_WinRegQueryValueEx.invoke(null, handle, toByteArray(attribute));
				METHOD_closeKey.invoke(systemRoot, handle);
			} else {
				int handle = (int) METHOD_openKey.invoke(user, toByteArray(key), KEY_READ, KEY_READ);
				valb = (byte[]) METHOD_WinRegQueryValueEx.invoke(null, handle, toByteArray(attribute));
				METHOD_closeKey.invoke(systemRoot, handle);
			}
