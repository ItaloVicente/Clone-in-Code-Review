		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(
								UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS,
								true);
			}
		});
