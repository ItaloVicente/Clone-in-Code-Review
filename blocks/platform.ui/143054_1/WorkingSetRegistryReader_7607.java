		if (element.getName().equals(IWorkbenchRegistryConstants.TAG_WORKING_SET)) {
			try {
				WorkingSetDescriptor desc = new WorkingSetDescriptor(element);
				registry.addWorkingSetDescriptor(desc);
			} catch (CoreException e) {
				WorkbenchPlugin.log("Unable to create working set descriptor.", e.getStatus());//$NON-NLS-1$
			}
			return true;
		}
