            }
            if (count < n) {
                System.arraycopy(ixs, 0, ixs = new int[count], 0, count);
            }
            listSetSelection(ixs);
            if (reveal) {
                listShowSelection();
            }
        }
    }

    /**
     * Returns the index of the given element in listMap, or -1 if the element cannot be found.
     * As of 3.3, uses the element comparer if available.
     *
     * @param element
     * @return the index
     */
    int getElementIndex(Object element) {
