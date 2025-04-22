		FileSystemExportOperation operation =
			new FileSystemExportOperation(
					null, resources, localDirectory, this);
		openTestWindow().run(true, true, operation);
		operation.setOverwriteFiles(true);
		operation.setCreateContainerDirectories(false);
		operation.setCreateLeadupStructure(false);
		openTestWindow().run(true, true, operation);

		verifyFolders(directoryNames.length);
