            IConfigurationElement[] hintElements = element
                    .getChildren(IWorkbenchRegistryConstants.TAG_HINT);
            for (IConfigurationElement hintElement : hintElements) {
                String id = hintElement
                        .getAttribute(IWorkbenchRegistryConstants.ATT_ID);
                String value = hintElement
                        .getAttribute(IWorkbenchRegistryConstants.ATT_VALUE);
