			Object handle = METHOD_openKey.invoke(USER_ROOT, toByteArray(key), KEY_SET, KEY_SET);
			int result = (Integer) METHOD_WinRegSetValueEx1.invoke(null, handle, toByteArray(attribute),
					toByteArray(value));
			METHOD_closeKey.invoke(USER_ROOT, handle);
			if (result != 0) {
						", value: " + value); //$NON-NLS-1$

			}
		} catch (Exception e) {
			throw new WinRegistryException(e.getMessage(), e);
