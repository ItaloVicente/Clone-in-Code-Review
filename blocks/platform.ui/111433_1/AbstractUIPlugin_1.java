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
