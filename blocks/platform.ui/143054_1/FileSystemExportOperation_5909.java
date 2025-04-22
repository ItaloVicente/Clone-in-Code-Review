				if (createContainerDirectories) {
					path = path.append(currentResource.getName());
					exporter.createFolder(path);
				}

				try {
					exportChildren(((IContainer) currentResource).members(),
							path);
				} catch (CoreException e) {
					errorTable.add(e.getStatus());
				}
			}
		}
	}

	public IStatus getStatus() {
		IStatus[] errors = new IStatus[errorTable.size()];
		errorTable.toArray(errors);
		return new MultiStatus(
				PlatformUI.PLUGIN_ID,
				IStatus.OK,
				errors,
				DataTransferMessages.FileSystemExportOperation_problemsExporting,
				null);
	}

	protected boolean isDescendent(List resources, IResource child) {
		if (child.getType() == IResource.PROJECT) {
