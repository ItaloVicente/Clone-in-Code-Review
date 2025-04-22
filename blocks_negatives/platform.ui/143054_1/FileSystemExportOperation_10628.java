        }

        return count;
    }

    /**
     *	Answer a boolean indicating the number of file resources that were
     *	specified for export
     *
     *	@return int
     */
    protected int countSelectedResources() throws CoreException {
        int result = 0;
        Iterator resources = resourcesToExport.iterator();

        while (resources.hasNext()) {
