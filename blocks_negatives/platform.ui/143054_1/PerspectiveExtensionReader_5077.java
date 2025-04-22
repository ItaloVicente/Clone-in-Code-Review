        return true;
    }

    /**
     * Process a view
     */
    private boolean processView(IConfigurationElement element) {
        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        String relative = element.getAttribute(IWorkbenchRegistryConstants.ATT_RELATIVE);
        String relationship = element.getAttribute(IWorkbenchRegistryConstants.ATT_RELATIONSHIP);
        String ratioString = element.getAttribute(IWorkbenchRegistryConstants.ATT_RATIO);
        boolean visible = !VAL_FALSE.equals(element.getAttribute(IWorkbenchRegistryConstants.ATT_VISIBLE));
        String closeable = element.getAttribute(IWorkbenchRegistryConstants.ATT_CLOSEABLE);
        String moveable = element.getAttribute(IWorkbenchRegistryConstants.ATT_MOVEABLE);
        String standalone = element.getAttribute(IWorkbenchRegistryConstants.ATT_STANDALONE);
        String showTitle = element.getAttribute(IWorkbenchRegistryConstants.ATT_SHOW_TITLE);

        String minVal = element.getAttribute(IWorkbenchRegistryConstants.ATT_MINIMIZED);
        boolean minimized = minVal != null && VAL_TRUE.equals(minVal);

        float ratio;

        if (id == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_ID);
            return false;
        }
        if (relationship == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_RELATIONSHIP);
            return false;
        }
