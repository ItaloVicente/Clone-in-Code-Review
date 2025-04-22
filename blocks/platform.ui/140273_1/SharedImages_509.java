		ImageDescriptor desc = WorkbenchImages.getImageDescriptor(symbolicName);
		if (desc != null) {
			WorkbenchImages.getImageRegistry().put(symbolicName, desc);
			return WorkbenchImages.getImageRegistry().get(symbolicName);
		}
		return null;
	}
