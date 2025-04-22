		/* fix for 11122 */
		boolean reuseDirty = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IPreferenceConstants.REUSE_DIRTY_EDITORS);
		if (!reuseDirty) {
			return null;
		}
