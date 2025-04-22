
			if (enableMru != null) {
				IEclipsePreferences prefs = getSwtRendererPreferences();
				prefs.putBoolean(StackRenderer.MRU_KEY, enableMru.getSelection());
				try {
					prefs.flush();
				} catch (BackingStoreException e) {
					WorkbenchPlugin.log("Failed to set SWT renderer preferences", e); //$NON-NLS-1$
				}
			}
