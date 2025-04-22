                if (!result) {
                            type
                            element.getDeclaringExtension()
                                    .getUniqueIdentifier());
                }
            }
        }
        return true;
    }

    /**
     * Process a perspective shortcut
     */
    private boolean processPerspectiveShortcut(IConfigurationElement element) {
        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (id != null) {
