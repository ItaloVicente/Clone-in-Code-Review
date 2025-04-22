		dialogSettings = createEmptySettings();
		boolean loaded = loadDialogSettingsFromWorkspace();
		if (!loaded) {
			loaded = loadDefaultDialogSettingsFromProduct();
		}
		if (!loaded) {
			loadDefaultDialogSettingsFromBundle();
		}
	}

	/**
	 * @return true if the product specific settings file was successfully read
	 */
	private boolean loadDefaultDialogSettingsFromProduct() {
		String rootUrl = PlatformUI.getPreferenceStore().getString(KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL);
		if (rootUrl == null || rootUrl.isEmpty()) {
			return false;
		}
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
			if (WorkbenchPlugin.DEBUG) {
				getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(),
						"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			}
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

	/**
	 * @return true if the workspace settings file was successfully read
	 */
	private boolean loadDialogSettingsFromWorkspace() {
		IPath dataLocation = getStateLocationOrNull();
		if (dataLocation == null) {
			return false;
		}
		String readWritePath = dataLocation.append(FN_DIALOG_SETTINGS).toOSString();
		File settingsFile = new File(readWritePath);
		if (settingsFile.exists()) {
			try {
				dialogSettings.load(readWritePath);
			} catch (IOException e) {
				dialogSettings = createEmptySettings();
				getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(),
						"Failed to load dialog settings from: " + settingsFile, e)); //$NON-NLS-1$
			}
			return true;
		}
		return false;
	}

	private void loadDefaultDialogSettingsFromBundle() {
		Bundle bundle = getBundle();
		URL dsURL = BundleUtility.find(bundle, FN_DIALOG_SETTINGS);
		if (dsURL == null) {
			return;
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(dsURL.openStream(), StandardCharsets.UTF_8))) {
			dialogSettings.load(reader);
		} catch (IOException e) {
			getLog().log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to load dialog settings from: " + dsURL, e)); //$NON-NLS-1$
			dialogSettings = createEmptySettings();
		}
	}

	private DialogSettings createEmptySettings() {
