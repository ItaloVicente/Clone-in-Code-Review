        String retarget = actionElement
                .getAttribute(IWorkbenchRegistryConstants.ATT_RETARGET);
        if (retarget != null && Boolean.valueOf(retarget).booleanValue()) {
            String allowLabelUpdate = actionElement
                    .getAttribute(IWorkbenchRegistryConstants.ATT_ALLOW_LABEL_UPDATE);
            String label = actionElement
                    .getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
