			((PreferencePageEnhancer) Tweaklets.get(PreferencePageEnhancer.KEY)).performDefaults();
			engine.setTheme(defaultTheme, true);
			if (engine.getActiveTheme() != null) {
				themeIdCombo.setSelection(new StructuredSelection(engine.getActiveTheme()));
			}
			IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
			enableAnimations.setSelection(apiStore
					.getDefaultBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS));
			useColoredLabels.setSelection(apiStore
					.getDefaultBoolean(IWorkbenchPreferenceConstants.USE_COLORED_LABELS));
