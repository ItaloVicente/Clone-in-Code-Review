        }
        return result;
    }

    /**
     *	Returns the number of items that are checked in the tree viewer.
     *
     *	@return The number of items that are checked
     */
    public int getCheckedElementCount() {
        return checkedStateStore.size();
    }

    /**
     * Get the full label of the treeElement (its name and its parent's name).
     * @param treeElement - the element being exported
     * @param parentLabel - the label of the parent, can be null
     * @return String
     */
    private String getFullLabel(Object treeElement, String parentLabel) {
        String label = parentLabel;
        if (parentLabel == null){
        }
        IPath parentName = new Path(label);

        String elementText = treeLabelProvider.getText(treeElement);
        if(elementText == null) {
