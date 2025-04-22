	private boolean loadDefaultDialogSettingsFromProduct() {
		String rootUrl = PlatformUI.getPreferenceStore().getString(KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL);
		if (rootUrl == null || rootUrl.isEmpty()) {
			return false;
		}
		String bundlePart = getBundle().getSymbolicName() + "/" + FN_DIALOG_SETTINGS; //$NON-NLS-1$
		String fullUrl = rootUrl.endsWith("/") ? rootUrl + bundlePart : rootUrl + "/" + bundlePart; //$NON-NLS-1$//$NON-NLS-2$
		URL url;
		try {
			url = new URL(fullUrl);
		} catch (MalformedURLException e) {
			getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(),
					"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			return false;
		}

		try {
			url = FileLocator.resolve(url);
		} catch (IOException e) {
			getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(),
					"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			return false;
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
			dialogSettings.load(reader);
			return true;
		} catch (IOException e) {
			dialogSettings = createEmptySettings();
			getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(),
					"Failed to load dialog settings from: " + url, e)); //$NON-NLS-1$
		}
		return false;
	}

