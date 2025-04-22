	public static boolean inPlaceEditorSupported() {
		if (PrefUtil.getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.DISABLE_OPEN_EDITOR_IN_PLACE)) {
			return false;
		}
		return Util.isWindows();
	}
