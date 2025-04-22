	public IWorkingSetPage getDefaultWorkingSetPage() {
		WorkingSetDescriptor descriptor = workingSetDescriptors.get(DEFAULT_PAGE_ID);

		if (descriptor != null) {
			return descriptor.createWorkingSetPage();
		}
		return null;
	}

	public WorkingSetDescriptor getWorkingSetDescriptor(String pageId) {
		return workingSetDescriptors.get(pageId);
	}

	public WorkingSetDescriptor[] getWorkingSetDescriptors() {
		return workingSetDescriptors.values().toArray(new WorkingSetDescriptor[workingSetDescriptors.size()]);
	}

	public WorkingSetDescriptor[] getNewPageWorkingSetDescriptors() {
		Collection descriptors = workingSetDescriptors.values();
		List result = new ArrayList(descriptors.size());
		for (Iterator iter = descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor = (WorkingSetDescriptor) iter.next();
