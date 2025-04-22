    /**
     * Read the project nature images within a registry.
     */
    public void readProjectNatureImages(IExtensionRegistry in,
            ProjectImageRegistry out) {
        registry = out;
        readRegistry(in, IDEWorkbenchPlugin.IDE_WORKBENCH,
                IDEWorkbenchPlugin.PL_PROJECT_NATURE_IMAGES);
    }
