    /**
     * Sets the state of the activity prompt button from preferences.
     */
    private void setActivityButtonState() {
        activityPromptButton.setSelection(getPreferenceStore().getBoolean(
                IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT));
    }
