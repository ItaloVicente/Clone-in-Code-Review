	}

	public WorkingSetDescriptor[] getWorkingSetDescriptors() {
		return workingSetDescriptors.values().toArray(new WorkingSetDescriptor[workingSetDescriptors.size()]);
	}

	public WorkingSetDescriptor[] getNewPageWorkingSetDescriptors() {
		Collection descriptors = workingSetDescriptors.values();
		List result = new ArrayList(descriptors.size());
		for (Iterator iter = descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor = (WorkingSetDescriptor) iter.next();
