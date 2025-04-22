	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		String sectionName = getClass().getSimpleName() + "_dialogBounds"; //$NON-NLS-1$
		IDialogSettings settings = org.eclipse.egit.ui.Activator.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(sectionName);
		if (section == null) {
			section = settings.addNewSection(sectionName);
		}
		return section;
	}

	@Override
	public boolean close() {
		IDialogSettings settings = org.eclipse.egit.ui.Activator.getDefault().getDialogSettings();
		saveSashWeights(sashForm, settings);

		return super.close();
	}

	private void restoreSashWeights(SashForm form, IDialogSettings settings, int[] defaults) {
		Control[] children = form.getChildren();
		if (children.length > 0) {
			int[] weights = new int[children.length];
			boolean storedWeightsOk = true;
			for (int i = 0; i < children.length; i++) {
				try {
					weights[i] = settings.getInt(SASH_WEIGHTS_PREF_PREFIX + i);
				} catch (NumberFormatException e) {
					storedWeightsOk = false;
					break;
				}
			}
			form.setWeights(storedWeightsOk ? weights : defaults);
		}
	}

	private void saveSashWeights(SashForm form, IDialogSettings settings) {
		int[] weights = form.getWeights();
		for (int i = 0; i < weights.length; i++) {
			settings.put(SASH_WEIGHTS_PREF_PREFIX + i, weights[i]);
		}
	}

