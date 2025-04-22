				final IPreferenceStore store = WorkbenchPlugin.getDefault()
						.getPreferenceStore();
				final boolean stickyCycle = store
						.getBoolean(IPreferenceConstants.STICKY_CYCLE);
				if ((!stickyCycle && (firstKey || quickReleaseMode))
						&& keyCode == stateMask) {
