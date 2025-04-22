        System.out.println("Comparisons required by lazy collection: " + comparator.comparisons);
        System.out.println("Comparisons required by reference implementation: " + comparisonComparator.comparisons);
        System.out.println("");

        super.tearDown();
    }

    /**
     * Computes the entries that are expected to lie in the given range. The result
     * is sorted.
     *
     * @param start
     * @param length
     * @return
     * @since 3.1
     */
    private Object[] computeExpectedElementsInRange(int start, int length) {
        int counter = 0;
