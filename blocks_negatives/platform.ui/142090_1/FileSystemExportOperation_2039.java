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

    /**
     * Returns the status of the export operation.
     * If there were any errors, the result is a status object containing
     * individual status objects for each error.
     * If there were no errors, the result is a status object with error code <code>OK</code>.
     *
     * @return the status
     */
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

    /**
     *  Answer a boolean indicating whether the passed child is a descendent
     *  of one or more members of the passed resources collection
     *
     *  @return boolean
     *  @param resources java.util.List
     *  @param child org.eclipse.core.resources.IResource
     */
    protected boolean isDescendent(List resources, IResource child) {
        if (child.getType() == IResource.PROJECT) {
