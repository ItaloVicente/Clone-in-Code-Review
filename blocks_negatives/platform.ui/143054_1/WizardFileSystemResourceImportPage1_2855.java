                if (o instanceof MinimizedFileSystemElement) {
                    MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
                    return element.getFiles(
                    		fileSystemStructureProvider).getChildren(
                            element);
                }
                return new Object[0];
            }
        };
    }

    /**
     *	Answer the root FileSystemElement that represents the contents of
     *	the currently-specified source.  If this FileSystemElement is not
     *	currently defined then create and return it.
     */
    protected MinimizedFileSystemElement getFileSystemTree() {

        File sourceDirectory = getSourceDirectory();
        if (sourceDirectory == null) {
