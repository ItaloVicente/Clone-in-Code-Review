		if (exportResource.getType() == IResource.FILE) {
			String destinationName = createDestinationName(leadupDepth, exportResource);
			monitor.subTask(destinationName);

			try {
				exporter.write((IFile) exportResource, destinationName);
			} catch (IOException e) {
				addError(NLS.bind(DataTransferMessages.DataTransfer_errorExporting, exportResource.getFullPath().makeRelative(), e.getMessage()), e);
			} catch (CoreException e) {
				addError(NLS.bind(DataTransferMessages.DataTransfer_errorExporting, exportResource.getFullPath().makeRelative(), e.getMessage()), e);
			}

			monitor.worked(1);
			ModalContext.checkCanceled(monitor);
		} else {
			IResource[] children = null;

			try {
				children = ((IContainer) exportResource).members();
			} catch (CoreException e) {
				addError(NLS.bind(DataTransferMessages.DataTransfer_errorExporting, exportResource.getFullPath()), e);
			}

			if (children.length == 0) { // create an entry for empty containers, see bug 278402
				String destinationName = createDestinationName(leadupDepth, exportResource);
				try {
					exporter.write((IContainer) exportResource, destinationName + IPath.SEPARATOR);
				} catch (IOException e) {
					addError(NLS.bind(DataTransferMessages.DataTransfer_errorExporting, exportResource.getFullPath().makeRelative(), e.getMessage()), e);
				}
			}

			for (IResource child : children) {
