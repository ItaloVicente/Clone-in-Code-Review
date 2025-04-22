		return (WorkingSetDescriptor[]) result.toArray(new WorkingSetDescriptor[result.size()]);
	}

	public boolean hasNewPageWorkingSetDescriptor() {
		Collection descriptors = workingSetDescriptors.values();
		for (Iterator iter = descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor = (WorkingSetDescriptor) iter.next();
