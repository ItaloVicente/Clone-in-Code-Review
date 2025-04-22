	private static void saveDialogSettings(SashForm sashForm,
			IDialogSettings dialogSettings) {
		if (dialogSettings != null) {
			int[] weights = sashForm.getWeights();
			String[] weightStrings = new String[weights.length];
			for (int i = 0; i < weights.length; i++) {
				weightStrings[i] = String.valueOf(weights[i]);
			}
			dialogSettings.put(SASH_WEIGHTS_SETTING, weightStrings);
		}
	}

	private static void initializeSashWeights(SashForm sashForm,
			int[] defaultValues, IDialogSettings dialogSettings) {
		if (dialogSettings != null) {
			String[] weightStrings = dialogSettings
					.getArray(SASH_WEIGHTS_SETTING);
			if (weightStrings != null
					&& weightStrings.length == defaultValues.length) {
				try {
					int[] weights = new int[weightStrings.length];
					for (int i = 0; i < weights.length; i++) {
						weights[i] = Integer.parseInt(weightStrings[i]);
					}
					sashForm.setWeights(weights);
					return;
				} catch (NumberFormatException ignore) { // bad settings
					dialogSettings.put(SASH_WEIGHTS_SETTING, (String[]) null);
				}
			}
		}
		sashForm.setWeights(defaultValues);
	}

