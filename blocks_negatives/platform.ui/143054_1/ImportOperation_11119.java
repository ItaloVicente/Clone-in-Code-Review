    }

    /**
     * Returns the rejected files based on the given multi status.
     *
     * @param multiStatus multi status to use to determine file rejection
     * @param files source files
     * @return list of rejected files as absolute paths. Object type IPath.
     */
    ArrayList getRejectedFiles(IStatus multiStatus, IFile[] files) {
        ArrayList filteredFiles = new ArrayList();

        IStatus[] status = multiStatus.getChildren();
        for (int i = 0; i < status.length; i++) {
            if (status[i].isOK() == false) {
            	errorTable.add(status[i]);
            	filteredFiles.add(files[i].getFullPath());
            }
        }
        return filteredFiles;
    }

    /**
     * Returns the status of the import operation.
     * If there were any errors, the result is a status object containing
     * individual status objects for each error.
     * If there were no errors, the result is a status object with error code <code>OK</code>.
     *
     * @return the status
     */
    public IStatus getStatus() {
        IStatus[] errors = new IStatus[errorTable.size()];
        errorTable.toArray(errors);
        return new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.OK, errors,
                DataTransferMessages.ImportOperation_importProblems,
                null);
    }

    /**
     * Imports the specified file system object into the workspace.
     * If the import fails, adds a status object to the list to be returned by
     * <code>getResult</code>.
     *
     * @param fileObject the file system object to be imported
     * @param policy determines how the file object is imported
     */
