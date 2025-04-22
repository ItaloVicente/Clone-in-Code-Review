			}
		}

		try {
			exporter.write(file, fullPath);
		} catch (IOException e) {
			errorTable.add(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
					NLS.bind(DataTransferMessages.DataTransfer_errorExporting, fullPath, e.getMessage()), e));
		} catch (CoreException e) {
			errorTable.add(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
					NLS.bind(DataTransferMessages.DataTransfer_errorExporting, fullPath, e.getMessage()), e));
		}

		monitor.worked(1);
		ModalContext.checkCanceled(monitor);
	}

	protected void exportSpecifiedResources() throws InterruptedException {
		Iterator resources = resourcesToExport.iterator();
		IPath initPath = (IPath) path.clone();

		while (resources.hasNext()) {
			IResource currentResource = (IResource) resources.next();
