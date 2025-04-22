	public static File getGpgExecutable() {
		String result = Platform.getPreferencesService().getString(
				Activator.PLUGIN_ID, GitCorePreferences.core_gpgExecutable,
				null, null);
		if (!StringUtils.isEmptyOrNull(result)) {
			try {
				File exe = Paths.get(result).toFile();
				if (exe.isFile() && exe.canExecute()) {
					return exe.getAbsoluteFile();
				} else {
					Activator.logError(MessageFormat.format(
							CoreText.GitSettings_gpgNotExecutable, result),
							null);
				}
			} catch (Exception e) {
				Activator.logError(MessageFormat.format(
						CoreText.GitSettings_gpgInvalidExecutable, result),
						e);
			}
		}
		return null;
	}

