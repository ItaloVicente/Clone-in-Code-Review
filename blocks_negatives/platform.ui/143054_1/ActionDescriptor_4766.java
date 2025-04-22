        String label = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
        String tooltip = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_TOOLTIP);
        String helpContextId = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_HELP_CONTEXT_ID);
        String mpath = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_MENUBAR_PATH);
        String tpath = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_TOOLBAR_PATH);
        String style = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_STYLE);
        String icon = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
        String hoverIcon = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_HOVERICON);
        String disabledIcon = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_DISABLEDICON);
        String description = actionElement.getAttribute(IWorkbenchRegistryConstants.TAG_DESCRIPTION);
        String accelerator = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ACCELERATOR);
        	mode  = ActionContributionItem.MODE_FORCE_TEXT;
        }

        if (label == null) {
            WorkbenchPlugin
            label = WorkbenchMessages.ActionDescriptor_invalidLabel;
        }

        String mgroup = null;
        String tgroup = null;
        if (mpath != null) {
            int loc = mpath.lastIndexOf('/');
            if (loc != -1) {
                mgroup = mpath.substring(loc + 1);
                mpath = mpath.substring(0, loc);
            } else {
                mgroup = mpath;
                mpath = null;
            }
        }
        if (targetType == T_POPUP && mgroup == null) {
