		return true;
	}

	private String openPerspectiveSetting() {
		return PrefUtil.getAPIPreferenceStore().getString(IWorkbenchPreferenceConstants.OPEN_NEW_PERSPECTIVE);
	}

	@Override
