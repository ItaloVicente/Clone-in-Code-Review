				final IPreferenceStore store = WorkbenchPlugin.getDefault()
						.getPreferenceStore();
				final boolean stickyCycle = store
						.getBoolean(IPreferenceConstants.STICKY_CYCLE);
				if (!isFiltered() && (!stickyCycle && (firstKey || quickReleaseMode))
						&& keyCode == stateMask) {
