        IConfigurationElement[] elements = extension.getConfigurationElements();
        for (IConfigurationElement element : elements) {
            if (element.getName().equals(
                    IWorkbenchRegistryConstants.TAG_TRIGGERPOINT)) {
                String id = element
                        .getAttribute(IWorkbenchRegistryConstants.ATT_ID);
                if (id == null) {
