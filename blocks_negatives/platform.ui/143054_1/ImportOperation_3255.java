            }
        } catch (CoreException e) {
            errorTable.add(e.getStatus());
        }
    }

    /**
     * Returns the container resource that the passed file system object should be
     * imported into.
     *
     * @param fileSystemObject the file system object being imported
     * @return the container resource that the passed file system object should be
     *     imported into
     * @exception CoreException if this method failed
     */
    IContainer getDestinationContainerFor(Object fileSystemObject)
            throws CoreException {
        IPath pathname = new Path(provider.getFullPath(fileSystemObject));

        if (createContainerStructure) {
