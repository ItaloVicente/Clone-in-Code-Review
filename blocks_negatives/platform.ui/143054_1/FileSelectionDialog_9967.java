                if (o instanceof FileSystemElement) {
                    return ((FileSystemElement) o).getFiles().getChildren(o);
                }
                return new Object[0];
            }
        };
    }

    /**
     * Returns a content provider for <code>FileSystemElement</code>s that returns
     * only folders as children.
     */
    private ITreeContentProvider getFolderProvider() {
        return new WorkbenchContentProvider() {
            @Override
