		ArchiveFileExportOperation operation =
			new ArchiveFileExportOperation(resources, filePath);

		operation.setCreateLeadupStructure(false);
		operation.setUseCompression(false);
		operation.setUseTarFormat(false);
		operation.run(new NullProgressMonitor());
		flattenPaths = true;
