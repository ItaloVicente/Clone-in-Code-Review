            contribution.contribute(menu, appendIfMissing, toolbar,
                    appendIfMissing);
        }
    }

    /**
     * This factory method returns a new ActionDescriptor for the
     * configuration element.  It should be implemented by subclasses.
     */
    protected abstract ActionDescriptor createActionDescriptor(
            IConfigurationElement element);

    /**
     * Factory method to create the helper contribution class that will hold
     * onto the menus and actions contributed.
     */
    protected BasicContribution createContribution() {
        return new BasicContribution();
    }

    /**
     * Returns the name of the part ID attribute that is expected
     * in the target extension.
     */
    protected String getTargetID(IConfigurationElement element) {
        String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_TARGET_ID);
    }

    /**
     * Returns the id of this contributions.
     */
    protected String getID(IConfigurationElement element) {
        String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
    }

    /**
     * Reads the contributions from the registry for the provided workbench
     * part and the provided extension point ID.
     */
    protected void readContributions(String id, String tag,
            String extensionPoint) {
        cache = null;
        currentContribution = null;
        targetID = id;
        targetContributionTag = tag;
        readRegistry(Platform.getExtensionRegistry(), PlatformUI.PLUGIN_ID,
                extensionPoint);
    }

    /**
     * Implements abstract method to handle the provided XML element
     * in the registry.
     */
    @Override
