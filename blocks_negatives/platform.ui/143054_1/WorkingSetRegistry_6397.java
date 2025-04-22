    /**
     * Returns the working set page with the given id.
     *
     * @param pageId working set page id
     * @return the working set page with the given id.
     */
    public IWorkingSetPage getWorkingSetPage(String pageId) {
        WorkingSetDescriptor descriptor = (WorkingSetDescriptor) workingSetDescriptors
                .get(pageId);

        if (descriptor == null) {
            return null;
        }
        return descriptor.createWorkingSetPage();
    }

    /**
     * Loads the working set registry.
     */
    public void load() {
        WorkingSetRegistryReader reader = new WorkingSetRegistryReader();
        reader.readWorkingSets(Platform.getExtensionRegistry(), this);
    }
