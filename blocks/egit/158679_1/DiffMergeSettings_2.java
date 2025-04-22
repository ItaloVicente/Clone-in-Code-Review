
	@Nullable
	public static String getDiffToolCommandFromPreferences(String filePath) {
		DiffToolMode diffToolMode = getDiffToolMode();
		if (diffToolMode == DiffToolMode.EXTERNAL_FOR_TYPE) {
			String fileExtension = getFileExtension(filePath);
			if (!StringUtils.isEmptyOrNull(fileExtension)) {
				String preference = getExternalDiffToolPreference();
				String[] tools = preference.split(","); //$NON-NLS-1$
				for (int i = 0; i < tools.length; i += 2) {
					String extension = tools[i].trim();
					String command = tools[i + 1].trim();
					if (Objects.equals(extension, fileExtension)) {
						return command;
					}
				}
			}
		}
		return null;
	}

	public static String getExternalDiffToolPreference() {
		String preference = Platform.getPreferencesService().getString(
				Activator.getPluginId(),
				UIPreferences.EXTERNAL_DIFF_TOOL_FOR_EXTENSION,
				"", //$NON-NLS-1$
				null);
		return preference;
	}

	public static String getFileExtension(String path) {
		int index = path.lastIndexOf('.');
		if (index == -1) {
			return ""; //$NON-NLS-1$
		}
		if (index == (path.length() - 1)) {
			return ""; //$NON-NLS-1$
		}
		return path.substring(index + 1);
	}
