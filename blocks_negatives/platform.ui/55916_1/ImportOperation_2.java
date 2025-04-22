        try {
            if (createVirtualFolder || createLinks || createLinkFilesOnly) {
	            if (targetResource.exists())
	            	targetResource.delete(true, null);
                targetResource.createLink(createRelativePath(
                        new Path(provider
                                .getFullPath(fileObject)), targetResource), 0, null);
            } else {
            if (targetResource.exists()) {
	            	if (targetResource.isLinked()) {
		            	targetResource.delete(true, null);
		            	targetResource.create(contentStream, false, null);
	            	}
	            	else
				targetResource.setContents(contentStream,
                        IResource.KEEP_HISTORY, null);
	            }
                else
                    targetResource.create(contentStream, false, null);
            }
            setResourceAttributes(targetResource, fileObject);

            if (provider instanceof TarLeveledStructureProvider) {
            	try {
            		targetResource.setResourceAttributes(((TarLeveledStructureProvider) provider).getResourceAttributes(fileObject));
            	} catch (CoreException e) {
            		errorTable.add(e.getStatus());
            	}
            }
        } catch (CoreException e) {
            errorTable.add(e.getStatus());
        } finally {
            try {
                contentStream.close();
            } catch (IOException e) {
                errorTable
                        .add(new Status(
                                IStatus.ERROR,
                                PlatformUI.PLUGIN_ID,
                                0,
                                NLS.bind(DataTransferMessages.ImportOperation_closeStreamError, fileObjectPath),
                                e));
            }
        }
    }
