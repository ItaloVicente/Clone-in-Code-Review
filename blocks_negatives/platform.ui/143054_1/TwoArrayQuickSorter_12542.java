            return fIgnoreCase ? ((String) left)
                    .compareToIgnoreCase((String) right) : ((String) left)
                    .compareTo((String) right);
        }
    }

    /**
     * Creates a sorter with default string comparator.
     * The keys are assumed to be strings.
     * @param ignoreCase specifies whether sorting is case sensitive or not.
     */
    public TwoArrayQuickSorter(boolean ignoreCase) {
        fComparator = new StringComparator(ignoreCase);
    }

    /**
     * Creates a sorter with a comparator.
     * @param comparator the comparator to order the elements. The comparator must not be <code>null</code>.
     */
    public TwoArrayQuickSorter(Comparator comparator) {
        fComparator = comparator;
    }

    /**
     * Sorts keys and values in parallel.
     * @param keys   the keys to use for sorting.
     * @param values the values associated with the keys.
     */
    public void sort(Object[] keys, Object[] values) {
        if ((keys == null) || (values == null)) {
            Assert.isTrue(false, "Either keys or values in null"); //$NON-NLS-1$
            return;
        }

        if (keys.length <= 1) {
