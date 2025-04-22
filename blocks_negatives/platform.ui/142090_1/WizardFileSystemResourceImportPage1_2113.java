        updateWidgetEnablements();
        fileSystemStructureProvider.clearVisitedDirs();
        selectionGroup.setFocus();
    }

    /**
     * Creates and returns a <code>FileSystemElement</code> if the specified
     * file system object merits one.  The criteria for this are:
     * Also create the children.
     */
    protected MinimizedFileSystemElement createRootElement(
            Object fileSystemObject, IImportStructureProvider provider) {
        boolean isContainer = provider.isFolder(fileSystemObject);
        String elementLabel = provider.getLabel(fileSystemObject);

        MinimizedFileSystemElement dummyParent = new MinimizedFileSystemElement(
                "", null, true);//$NON-NLS-1$
        dummyParent.setPopulated();
        MinimizedFileSystemElement result = new MinimizedFileSystemElement(
                elementLabel, dummyParent, isContainer);
        result.setFileSystemObject(fileSystemObject);

        result.getFiles(provider);

        return dummyParent;
    }

    /**
     *	Create the import source specification widgets
     */
    @Override
