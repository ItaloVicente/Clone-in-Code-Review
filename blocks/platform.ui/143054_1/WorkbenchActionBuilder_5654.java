	}

	private void makeFeatureDependentActions(IWorkbenchWindow window) {
		AboutInfo[] infos = null;

		IPreferenceStore prefs = IDEWorkbenchPlugin.getDefault().getPreferenceStore();

		String stateKey = "platformState"; //$NON-NLS-1$
		String prevState = prefs.getString(stateKey);
		String currentState = String.valueOf(Platform.getStateStamp());
		boolean sameState = currentState.equals(prevState);
		if (!sameState) {
			prefs.putValue(stateKey, currentState);
		}

		String quickStartKey = IDEActionFactory.QUICK_START.getId();
		String showQuickStart = prefs.getString(quickStartKey);
		if (sameState && "true".equals(showQuickStart)) { //$NON-NLS-1$
			quickStartAction = IDEActionFactory.QUICK_START.create(window);
