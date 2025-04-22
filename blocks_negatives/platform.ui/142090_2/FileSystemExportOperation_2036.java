                IPath destination = currentPath.append(child.getName());
                exporter.createFolder(destination);
                try {
                    exportChildren(((IContainer) child).members(), destination);
                } catch (CoreException e) {
                    errorTable.add(e.getStatus());
                }
            }
        }
    }

    /**
     *  Export the passed file to the specified location
     *
     *  @param file org.eclipse.core.resources.IFile
     *  @param location org.eclipse.core.runtime.IPath
     */
    protected void exportFile(IFile file, IPath location)
            throws InterruptedException {
        IPath fullPath = location.append(file.getName());
        monitor.subTask(file.getFullPath().toString());
        String properPathString = fullPath.toOSString();
        File targetFile = new File(properPathString);

        if (targetFile.exists()) {
            if (!targetFile.canWrite()) {
                errorTable.add(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                        0, NLS.bind(DataTransferMessages.DataTransfer_cannotOverwrite, targetFile.getAbsolutePath()),
                        null));
                monitor.worked(1);
                return;
            }

            if (overwriteState == OVERWRITE_NONE) {
