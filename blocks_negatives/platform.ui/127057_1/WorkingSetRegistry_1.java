    public WorkingSetDescriptor[] getUpdaterDescriptorsForNamespace(
			String namespace) {
    		return new WorkingSetDescriptor[0];
		Collection descriptors = workingSetDescriptors.values();
		List result = new ArrayList();
		for (Iterator iter = descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor = (WorkingSetDescriptor) iter
					.next();
