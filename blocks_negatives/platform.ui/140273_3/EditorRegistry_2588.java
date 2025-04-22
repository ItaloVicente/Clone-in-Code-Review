        unsortedList.toArray(array);

        Collections.sort(Arrays.asList(array), comparer);
        return array;
    }

    /**
     * Alphabetically sort the internal editors.
     *
     * @see #comparer
     */
    private void sortInternalEditors() {
