			Object handle = METHOD_openKey.invoke(USER_ROOT, toByteArray(key), KEY_READ, KEY_READ);
			byte[] valb = (byte[]) METHOD_WinRegQueryValueEx.invoke(null, handle, toByteArray(attribute));
			METHOD_closeKey.invoke(USER_ROOT, handle);
			return (valb != null ? toString(valb) : null);
		} catch (Exception e) {
			throw new WinRegistryException(e.getMessage(), e);
