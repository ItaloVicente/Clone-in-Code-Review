    }

    /**
     * Returns an array of all working set descriptors.
     *
     * @return an array of all working set descriptors.
     */
    public WorkingSetDescriptor[] getWorkingSetDescriptors() {
		return workingSetDescriptors.values().toArray(
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
