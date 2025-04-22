        String key = mappingKeyFor(ext);
        return typeEditorMappings.get(key);
    }

    /**
     * Find the file editor mappings for the given filename.
     * <p>
     * Return an array of two FileEditorMapping items, where the first mapping
     * is for the entire filename, and the second mapping is for the filename's
     * extension only. These items can be null if no mapping exist on the
     * filename and/or filename's extension.</p>
     *
     * @param filename the filename
     * @return the mappings
     */
    private FileEditorMapping[] getMappingForFilename(String filename) {
        FileEditorMapping[] mapping = new FileEditorMapping[2];

        mapping[0] = getMappingFor(filename);

        int index = filename.lastIndexOf('.');
        if (index > -1) {
            String extension = filename.substring(index);
        }

        return mapping;
    }

    /**
     * Return the editor descriptors pulled from the OS.
     * <p>
     * WARNING! The image described by each editor descriptor is *not* known by
     * the workbench's graphic registry. Therefore clients must take care to
     * ensure that if they access any of the images held by these editors that
     * they also dispose them
     * </p>
     * @return the editor descriptors
     */
    public IEditorDescriptor[] getSortedEditorsFromOS() {
