        ArchiveFileExportOperation operation =
        	new ArchiveFileExportOperation(resources, filePath);

        operation.setCreateLeadupStructure(false);
        operation.setUseCompression(true);
        operation.setUseTarFormat(true);
        operation.run(new NullProgressMonitor());
        flattenPaths = true;
