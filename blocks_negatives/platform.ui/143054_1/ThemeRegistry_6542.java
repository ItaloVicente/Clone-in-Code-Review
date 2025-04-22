        return (IThemeDescriptor) findDescriptor(getThemes(), id);
    }

    /**
     * @param descriptors
     * @param id
     * @return
     */
    private IThemeElementDefinition findDescriptor(
            IThemeElementDefinition[] descriptors, String id) {
        int idx = Arrays.binarySearch(descriptors, id, ID_COMPARATOR);
        if (idx < 0) {
