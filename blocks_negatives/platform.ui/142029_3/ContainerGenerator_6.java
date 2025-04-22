		    container = root;
		    for (int i = 0; i < containerFullPath.segmentCount(); i++) {
		        String currentSegment = containerFullPath.segment(i);
		        IResource resource = container.findMember(currentSegment);
		        if (resource != null) {
		        	if (resource.getType() == IResource.FILE) {
		        		String msg = NLS.bind(IDEWorkbenchMessages.ContainerGenerator_pathOccupied, resource.getFullPath().makeRelative());
		        		throw new CoreException(new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1, msg, null));
		        	}
		            container = (IContainer) resource;
