    /**
     * Adds a working set descriptor.
     *
     * @param descriptor working set descriptor to add. Must not
     * 	exist in the registry yet.
     */
    public void addWorkingSetDescriptor(WorkingSetDescriptor descriptor) {
		Assert.isTrue(!workingSetDescriptors.containsValue(descriptor),
		IExtensionTracker tracker = PlatformUI.getWorkbench()
				.getExtensionTracker();
		tracker.registerObject(descriptor.getConfigurationElement()
				.getDeclaringExtension(), descriptor,
