    public IWorkingSetPage getDefaultWorkingSetPage() {
        WorkingSetDescriptor descriptor = (WorkingSetDescriptor) workingSetDescriptors
                .get(DEFAULT_PAGE_ID);

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
        return (WorkingSetDescriptor) workingSetDescriptors.get(pageId);
    }

    /**
     * Returns an array of all working set descriptors.
     *
     * @return an array of all working set descriptors.
     */
    public WorkingSetDescriptor[] getWorkingSetDescriptors() {
        return (WorkingSetDescriptor[]) workingSetDescriptors.values().toArray(
                new WorkingSetDescriptor[workingSetDescriptors.size()]);
    }

    /**
     * Returns an array of all working set descriptors having
     * a page class attribute
     *
     * @return an array of all working set descriptors having a
     * page class attribute
     */
    public WorkingSetDescriptor[] getNewPageWorkingSetDescriptors() {
    	Collection descriptors= workingSetDescriptors.values();
        List result= new ArrayList(descriptors.size());
        for (Iterator iter= descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor= (WorkingSetDescriptor)iter.next();
