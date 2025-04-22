        openTestWindow().run(true, true, operation);

        verifyFiles(directoryNames.length, true);

    }

    /**
     * Verifies that all files were imported.
     *
     * @param folderCount number of folders that were imported
     */
    private void verifyFiles(int folderCount, boolean hasRootMembers) {
        try {
            IPath path = new Path(localDirectory);
            IResource targetFolder = project.findMember(path.makeRelative());

            assertTrue("Import failed", targetFolder instanceof IContainer);

            IResource[] resources = ((IContainer) targetFolder).members();
            if (!hasRootMembers){
	            assertEquals("Import failed to import all directories",
	                    folderCount, resources.length);
	            for (IResource resource : resources) {
	                assertTrue("Import failed", resource instanceof IContainer);
	                verifyFolder((IContainer) resource);
	            }
            }
            else {
            	for (IResource resource : resources) {
            		if (resource instanceof IContainer) {
