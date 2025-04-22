	/**
	 * Get the workbench image with the given path relative to
	 * ICON_PATH from the plugins image registry .
	 * @param relativePath
	 * @return ImageDescriptor
	 */
	ImageDescriptor getIDEImageDescriptor(String relativePath){
		if(imageRegistry==null){
			imageRegistry=IDEWorkbenchPlugin.getDefault().getImageRegistry();
		}
		ImageDescriptor descriptor=imageRegistry.getDescriptor(relativePath);
		if(descriptor==null){
			descriptor=IDEWorkbenchPlugin.getIDEImageDescriptor(relativePath);
			imageRegistry.put(relativePath, descriptor);
		}
		return descriptor;
	}
