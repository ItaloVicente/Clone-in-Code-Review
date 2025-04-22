        return compareNames(resource1, resource2);
    }

    /**
     * Returns the sort criteria of this this sorter.
     *
     * @return the sort criterion: one of <code>NAME</code> or <code>TYPE</code>
     */
    public int getCriteria() {
        return criteria;
    }

    /**
     * Returns the extension portion of the given resource.
     *
     * @param resource the resource
     * @return the file extension, possibily the empty string
     */
    private String getExtensionFor(IResource resource) {
        String ext = resource.getFileExtension();
    }
