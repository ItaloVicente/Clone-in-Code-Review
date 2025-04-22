		final List<WorkingSetDescriptor> descriptors = getUniqueDescriptors(symbolicName);
		final Map<WorkingSetDescriptor, List<IWorkingSet>> nonEmptyDescriptors = getNonEmpty(descriptors);
		if (nonEmptyDescriptors.isEmpty()) {
			return;
		}
		Job job = new WorkbenchJob(
				NLS.bind(WorkbenchMessages.AbstractWorkingSetManager_updatersActivating, symbolicName)) {
