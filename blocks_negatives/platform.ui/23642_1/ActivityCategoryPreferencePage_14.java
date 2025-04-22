        workbench.getActivitySupport().setEnabledActivityIds(
                workingCopy.getEnabledActivityIds());
        getPreferenceStore().setValue(
                IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT,
                activityPromptButton.getSelection());
        return true;
    }

    @Override
