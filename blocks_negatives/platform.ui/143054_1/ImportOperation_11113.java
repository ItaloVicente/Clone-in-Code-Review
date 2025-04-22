                }
            }
        }
    }

    /**
     * Creates the folders that appear in the specified resource path.
     * These folders are created relative to the destination container.
     *
     * @param path the relative path of the resource
     * @return the container resource coresponding to the given path
     * @exception CoreException if this method failed
     */
    IContainer createContainersFor(IPath path) throws CoreException {

        IContainer currentFolder = destinationContainer;

        int segmentCount = path.segmentCount();

        if (segmentCount == 0) {
