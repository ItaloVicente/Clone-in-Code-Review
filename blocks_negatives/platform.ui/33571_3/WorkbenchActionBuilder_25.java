    }

    /**
     * Creates the feature-dependent actions for the menu bar.
     */
    private void makeFeatureDependentActions(IWorkbenchWindow window) {
        AboutInfo[] infos = null;
        
        IPreferenceStore prefs = IDEWorkbenchPlugin.getDefault().getPreferenceStore();

        String prevState = prefs.getString(stateKey);
        String currentState = String.valueOf(Platform.getStateStamp());
        boolean sameState = currentState.equals(prevState);
        if (!sameState) {
        	prefs.putValue(stateKey, currentState);
        }
        
        String quickStartKey = IDEActionFactory.QUICK_START.getId();
        String showQuickStart = prefs.getString(quickStartKey);
            quickStartAction = IDEActionFactory.QUICK_START.create(window);
