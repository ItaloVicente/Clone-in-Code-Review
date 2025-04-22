		while (resources.hasNext()) {
			IResource currentResource = resources.next();
			exportResource(currentResource);
		}
	}

	public IStatus getStatus() {
		IStatus[] errors = new IStatus[errorTable.size()];
		errorTable.toArray(errors);
		return new MultiStatus(
				IDEWorkbenchPlugin.IDE_WORKBENCH,
				IStatus.OK,
				errors,
				DataTransferMessages.FileSystemExportOperation_problemsExporting,
				null);
	}

	protected void initialize() throws IOException {
		if(useTarFormat) {
			exporter = new TarFileExporter(destinationFilename, useCompression, resolveLinks);
		} else {
			exporter = new ZipFileExporter(destinationFilename, useCompression, resolveLinks);
		}
	}

