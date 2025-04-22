                themeDescriptor = readTheme(element);
                if (themeDescriptor != null) {
                    readElementChildren(element);
                    themeDescriptor = null;
                }
                return true;
            }
        } else if (themeDescriptor != null
                && elementName.equals(IWorkbenchRegistryConstants.TAG_DESCRIPTION)) {
            themeDescriptor.setDescription(element.getValue());
            return true;
        } else if (elementName.equals(IWorkbenchRegistryConstants.TAG_DATA)) {
            String name = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
            String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_VALUE);
            if (name == null || value == null) {
                logError(element, RESOURCE_BUNDLE.getString("Data.badData")); //$NON-NLS-1$
            } else {
                if (themeDescriptor != null) {
                    themeDescriptor.setData(name, value);
                } else {
                    themeRegistry.setData(name, value);
                    if (!dataMap.containsKey(name)) {
