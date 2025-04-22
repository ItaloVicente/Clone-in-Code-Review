			@Override
			public void run() {
				IEclipsePreferences prefs = getPrefs();
				prefs.putBoolean(PREFS_SYNCED, isChecked());
				try {
					prefs.flush();
				} catch (BackingStoreException e) {
				}
				if (isChecked()) {
					ISelectionService srv = (ISelectionService) getSite()
							.getService(ISelectionService.class);
					reactOnSelection(srv.getSelection());
				}
