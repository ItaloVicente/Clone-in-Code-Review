	public List<WorkingSetDescriptor> getUpdaterDescriptorsForNamespace(String namespace) {
		if (namespace == null) { // fix for Bug 84225
			return Collections.emptyList();
		}
		List<WorkingSetDescriptor> result = new ArrayList<>();
		for (WorkingSetDescriptor descriptor : workingSetDescriptors.values()) {
