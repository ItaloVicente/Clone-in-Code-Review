		if (preferencePage != null) {
			preferencePage.close();
			bot.waitUntil(Conditions.shellCloses(preferencePage));
		}
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				PreferencesUtil.createPreferenceDialogOn(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getShell(),
						GlobalConfigurationPreferencePage.ID, null, null)
						.open();
			}
		});
		bot.waitUntil(Conditions.shellIsActive("Preferences"));
		preferencePage = bot.shell("Preferences");
