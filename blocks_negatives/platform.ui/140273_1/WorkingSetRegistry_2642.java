        if (descriptor != null) {
            return descriptor.createWorkingSetPage();
        }
        return null;
    }

    /**
     * Returns the working set descriptor with the given id.
     *
     * @param pageId working set page id
     * @return the working set descriptor with the given id.
     */
    public WorkingSetDescriptor getWorkingSetDescriptor(String pageId) {
