                    return true;
                }
                return false;
            }
        };
    }

    /**
     * Returns a File object representing the currently-named source directory iff
     * it exists as a valid directory, or <code>null</code> otherwise.
     */
    protected File getSourceDirectory() {
        return getSourceDirectory(this.sourceNameField.getText());
    }

    /**
     * Returns a File object representing the currently-named source directory iff
     * it exists as a valid directory, or <code>null</code> otherwise.
     *
     * @param path a String not yet formatted for java.io.File compatability
     */
    private File getSourceDirectory(String path) {
        File sourceDirectory = new File(getSourceDirectoryName(path));
        if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
            return null;
        }

        return sourceDirectory;
    }

    /**
     *	Answer the directory name specified as being the import source.
     *	Note that if it ends with a separator then the separator is first
     *	removed so that java treats it as a proper directory
     */
    private String getSourceDirectoryName() {
        return getSourceDirectoryName(this.sourceNameField.getText());
    }

    /**
     *	Answer the directory name specified as being the import source.
     *	Note that if it ends with a separator then the separator is first
     *	removed so that java treats it as a proper directory
     */
    private String getSourceDirectoryName(String sourceName) {
        IPath result = new Path(sourceName.trim());

        if (result.getDevice() != null && result.segmentCount() == 0) {
