        super.performDefaults();
        activityPromptButton.setSelection(getPreferenceStore()
                .getDefaultBoolean(
                        IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT));
        
        Set defaultEnabled = new HashSet();
        Set activityIds = workingCopy.getDefinedActivityIds();
        for (Iterator i = activityIds.iterator(); i.hasNext();) {
            String activityId = (String) i.next();
            IActivity activity = workingCopy.getActivity(activityId);
            try {
                if (activity.isDefaultEnabled()) {
                    defaultEnabled.add(activityId);
                }
            } catch (NotDefinedException e) {
            }
        }
        
        workingCopy.setEnabledActivityIds(defaultEnabled);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
     *      java.lang.String, java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) {
        if (data instanceof Hashtable) {
            Hashtable table = (Hashtable)data;
            allowAdvanced = Boolean.valueOf((String) table.remove(ALLOW_ADVANCED)).booleanValue();
            strings.putAll(table);
        }
    }
    
    @Override
