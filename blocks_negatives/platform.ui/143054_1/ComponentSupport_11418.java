    /**
     * Returns whether the current platform has support
     * for system in-place editor.
     */
    public static boolean inPlaceEditorSupported() {
    	if (PrefUtil.getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.DISABLE_OPEN_EDITOR_IN_PLACE)) {
    		return false;
    	}
        return Util.isWindows();
    }
