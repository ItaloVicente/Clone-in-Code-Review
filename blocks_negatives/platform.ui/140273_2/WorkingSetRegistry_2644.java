        return (WorkingSetDescriptor[])result.toArray(new WorkingSetDescriptor[result.size()]);
    }

    /**
     * Returns <code>true</code> if there is a working set descriptor with
     * a page class attribute. Otherwise <code>false</code> is returned.
     *
     * @return whether a descriptor with a page class attribute exists
     */
    public boolean hasNewPageWorkingSetDescriptor() {
    	Collection descriptors= workingSetDescriptors.values();
        for (Iterator iter= descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor= (WorkingSetDescriptor)iter.next();
