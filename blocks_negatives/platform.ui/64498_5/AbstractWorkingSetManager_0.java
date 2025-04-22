    /**
     * Returns the descriptors for the given editable working set ids. If an id
     * refers to a missing descriptor, or one that is non-editable, it is
     * skipped. If <code>null</code> is passed, all editable descriptors are
     * returned.
     *
     * @param supportedWorkingSetIds
     *            the ids for the working set descriptors, or <code>null</code>
     *            for all editable descriptors
     * @return the descriptors corresponding to the given editable working set
     *         ids
     */
    private static WorkingSetDescriptor[] getSupportedEditableDescriptors(
            String[] supportedWorkingSetIds) {
        WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
                .getWorkingSetRegistry();
        if (supportedWorkingSetIds == null) {
            return registry.getNewPageWorkingSetDescriptors();
        }
        List result = new ArrayList(supportedWorkingSetIds.length);
        for (int i = 0; i < supportedWorkingSetIds.length; i++) {
            WorkingSetDescriptor desc = registry
                    .getWorkingSetDescriptor(supportedWorkingSetIds[i]);
            if (desc != null && desc.isEditable()) {
                result.add(desc);
            }
        }
        return (WorkingSetDescriptor[]) result
                .toArray(new WorkingSetDescriptor[result.size()]);
    }
