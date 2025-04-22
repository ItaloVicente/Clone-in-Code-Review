	private static WorkingSetDescriptor[] getSupportedEditableDescriptors(String[] supportedWorkingSetIds) {
		WorkingSetRegistry registry = WorkbenchPlugin.getDefault().getWorkingSetRegistry();
		if (supportedWorkingSetIds == null) {
			return registry.getNewPageWorkingSetDescriptors();
		}
		List<WorkingSetDescriptor> result = new ArrayList<>(supportedWorkingSetIds.length);
		for (int i = 0; i < supportedWorkingSetIds.length; i++) {
			WorkingSetDescriptor desc = registry.getWorkingSetDescriptor(supportedWorkingSetIds[i]);
			if (desc != null && desc.isEditable()) {
				result.add(desc);
			}
		}
		return result.toArray(new WorkingSetDescriptor[result.size()]);
	}
