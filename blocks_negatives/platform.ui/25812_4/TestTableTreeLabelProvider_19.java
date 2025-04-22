    /**
     * Returns the label text for a given column.
     *
     * @param element the object representing the entire row. Can be 
     *     <code>null</code> indicating that no input object is set in the viewer.
     * @param columnIndex the index of the column in which the label appears. Numbering is zero based.
     */
    public String getColumnText(Object element, int columnIndex) {
        if (element != null)
            return element.toString() + " column " + columnIndex;
        return null;
    }
