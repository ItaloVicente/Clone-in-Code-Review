        }
        return list;
    }

    /**
     * @param element the element to insert
     * @return the index where the item should be inserted.
     */
    protected int indexForElement(Object element) {
        ViewerComparator comparator = getComparator();
        if (comparator == null) {
