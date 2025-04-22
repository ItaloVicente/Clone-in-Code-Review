        	sourceDirectory = sourceDirectory.getParentFile();

        if (shouldImportTopLevelFoldersRecursively)
            operation = new ImportOperation(getContainerFullPath(),
                    sourceDirectory, fileSystemStructureProvider,
                    this, Arrays.asList(new File[] {getSourceDirectory()}));
        else
        	operation = new ImportOperation(getContainerFullPath(),
                sourceDirectory, fileSystemStructureProvider,
                this, fileSystemObjects);

        operation.setContext(getShell());
        return executeImportOperation(operation);
    }

    @Override
