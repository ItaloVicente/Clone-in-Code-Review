			IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
			boolean enableAnimations = preferenceStore
					.getBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS);
			preferenceStore.setValue(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS, false);

			List<MPerspective> persps = modelService.findElements(model, null, MPerspective.class,
					null);
			if (persps.size() > 1) {
				PrefUtil.getAPIPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_INTRO,
						false);
				PrefUtil.saveAPIPrefs();
			}
			getWindowAdvisor().postWindowCreate();
			getWindowAdvisor().openIntro();
