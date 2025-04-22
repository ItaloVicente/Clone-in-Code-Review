        boolean foundAtLeastOne = false;
        for (int i = 0; i < configElements.length; ++i) {
            IConfigurationElement element = configElements[i];
            if (element != null
                    && element.getName()
                            .equals(IWorkbenchConstants.TAG_STARTUP)) {
                runEarlyStartup(getExecutableExtension(element));
                foundAtLeastOne = true;
