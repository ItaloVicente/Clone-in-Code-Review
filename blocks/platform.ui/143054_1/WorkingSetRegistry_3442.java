	public IWorkingSetPage getWorkingSetPage(String pageId) {
		WorkingSetDescriptor descriptor = workingSetDescriptors.get(pageId);

		if (descriptor == null) {
			return null;
		}
		return descriptor.createWorkingSetPage();
	}

	public void load() {
		WorkingSetRegistryReader reader = new WorkingSetRegistryReader();
		reader.readWorkingSets(Platform.getExtensionRegistry(), this);
	}
