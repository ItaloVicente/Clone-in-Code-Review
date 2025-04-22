		try {
			if (createVirtualFolder || createLinks || createLinkFilesOnly) {
				if (targetResource.exists())
					targetResource.delete(true, subMonitor.newChild(50));
				targetResource.createLink(
						createRelativePath(new Path(provider.getFullPath(fileObject)), targetResource), 0,
						subMonitor.newChild(50));
			} else {
				if (targetResource.exists()) {
					if (targetResource.isLinked()) {
						targetResource.delete(true, subMonitor.newChild(50));
						targetResource.create(contentStream, false, subMonitor.newChild(50));
					} else
						targetResource.setContents(contentStream, IResource.KEEP_HISTORY, subMonitor.newChild(100));
				} else
					targetResource.create(contentStream, false, subMonitor.newChild(100));
			}
			setResourceAttributes(targetResource, fileObject);

			if (provider instanceof TarLeveledStructureProvider) {
				try {
					targetResource.setResourceAttributes(
							((TarLeveledStructureProvider) provider).getResourceAttributes(fileObject));
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
				errorTable.add(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
						NLS.bind(DataTransferMessages.ImportOperation_closeStreamError, fileObjectPath), e));
			}
		}
	}
