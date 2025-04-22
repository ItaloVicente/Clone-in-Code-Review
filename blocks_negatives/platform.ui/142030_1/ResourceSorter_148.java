    }

    /**
     * Returns a number reflecting the collation order of the given elements
     * based on their class.
     *
     * @param element1 the first element to be ordered
     * @param element2 the second element to be ordered
     * @return a negative number if the first element is less  than the
     *  second element; the value <code>0</code> if the first element is
     *  equal to the second element; and a positive number if the first
     *  element is greater than the second element
     */
    protected int compareClass(Object element1, Object element2) {
        return classComparison(element1) - classComparison(element2);
    }

    /**
     * Returns a number reflecting the collation order of the given resources
     * based on their resource names.
     *
     * @param resource1 the first resource element to be ordered
     * @param resource2 the second resource element to be ordered
     * @return a negative number if the first element is less  than the
     *  second element; the value <code>0</code> if the first element is
     *  equal to the second element; and a positive number if the first
     *  element is greater than the second element
     */
    protected int compareNames(IResource resource1, IResource resource2) {
        return collator.compare(resource1.getName(), resource2.getName());
    }

    /**
     * Returns a number reflecting the collation order of the given resources
     * based on their respective file extensions. Resources with the same file
     * extension will be collated based on their names.
     *
     * @param resource1 the first resource element to be ordered
     * @param resource2 the second resource element to be ordered
     * @return a negative number if the first element is less  than the
     *  second element; the value <code>0</code> if the first element is
     *  equal to the second element; and a positive number if the first
     *  element is greater than the second element
     */
    protected int compareTypes(IResource resource1, IResource resource2) {
        String ext1 = getExtensionFor(resource1);
        String ext2 = getExtensionFor(resource2);

        int result = collator.compare(ext1, ext2);

        if (result != 0) {
