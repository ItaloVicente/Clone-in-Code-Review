        return false;
    }

    /**
     * Sets which nodes are grayed in this viewer.
     * The given list contains the elements that are to be grayed;
     * all other nodes are to be ungrayed.
     * <p>
     * This method is typically used when restoring the interesting
     * state of a viewer captured by an earlier call to <code>getGrayedElements</code>.
     * </p>
     *
     * @param elements the array of grayed elements
     *
     * @see #getGrayedElements
     */
    public void setGrayedElements(Object[] elements) {
        assertElementsNotNull(elements);
        CustomHashtable set = newHashtable(elements.length * 2 + 1);
        for (Object element : elements) {
            set.put(element, element);
        }
        TableItem[] items = getTable().getItems();
        for (TableItem item : items) {
            Object element = item.getData();
            if (element != null) {
                boolean gray = set.containsKey(element);
                if (item.getGrayed() != gray) {
                    item.setGrayed(gray);
                }
            }
        }
    }
