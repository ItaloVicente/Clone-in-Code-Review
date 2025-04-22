			return Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, key, attribute);
		} catch (LastErrorException e) {
			if (e.getErrorCode() == WinError.ERROR_FILE_NOT_FOUND
					|| e.getErrorCode() == WinError.ERROR_PATH_NOT_FOUND) {
				return null;
			}
			throw new WinRegistryException("Unable to read registry. Key = " + key + attribute); //$NON-NLS-1$
