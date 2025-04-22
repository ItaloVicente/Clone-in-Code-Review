	String getEditorIdForLargeDocument(IEditorInput editorInput) {
		String editorId = null;

		String fileExtensionSeparator = "."; //$NON-NLS-1$
		String valueSeparator = "\\|"; //$NON-NLS-1$

		try {
			if (editorInput instanceof IPathEditorInput) {
				IPath path = ((IPathEditorInput) editorInput).getPath();
				String fileExtension = path.getFileExtension();
				if (fileExtension != null && !fileExtension.isEmpty()) {
					IPreferenceStore preferenceStore = PrefUtil.getInternalPreferenceStore();
					String largeFilePreferenceName = IPreferenceConstants.LARGE_FILE_SIZE_EXTENSIONS
							+ fileExtensionSeparator + fileExtension;
					String largeFilePreference = preferenceStore.getString(largeFilePreferenceName);
					if (largeFilePreference != null && !largeFilePreference.isEmpty()) {
						String[] preferenceValues = largeFilePreference.split(valueSeparator);
						if (preferenceValues.length % 2 != 0) {
							String errorMessage = NLS.bind(
									"Expected pairs of values separated by \"{0}\" for preference \"{1}\" with value \"{2}\"", //$NON-NLS-1$
									new String[] { valueSeparator, largeFilePreferenceName,
											Arrays.toString(preferenceValues) });
							throw new IllegalArgumentException(errorMessage);
						}

						File file = new File(path.toOSString());
						long fileSize = file.length();

						long maxBound = 0;
						for (int i = 0; i < preferenceValues.length; i = i + 2) {
							String boundString = preferenceValues[i + 0];
							String editorIdString = preferenceValues[i + 1];
							long bound = Long.valueOf(boundString);
							if (fileSize >= bound && bound >= maxBound) {
								maxBound = bound;
								editorId = editorIdString;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			WorkbenchPlugin.log("Exception occurred while checking for large file editor", e); //$NON-NLS-1$
		}
		return editorId;
	}

