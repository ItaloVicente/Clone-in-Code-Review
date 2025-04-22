			WorkbenchPlugin.getDefault().savePluginPreferences();
		} finally {
			saving = false;
		}
	}

	public void shutdown() {
		unhookListeners();
		saveEnabledStates();
	}
