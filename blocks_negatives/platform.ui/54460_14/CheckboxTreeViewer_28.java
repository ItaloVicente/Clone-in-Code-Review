	protected void setCheckedChildren(Item item, boolean state) {
        createChildren(item);
        Item[] items = getChildren(item);
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                Item it = items[i];
                if (it.getData() != null && (it instanceof TreeItem)) {
                    TreeItem treeItem = (TreeItem) it;
                    treeItem.setChecked(state);
                    setCheckedChildren(treeItem, state);
                }
            }
        }
    }

    /**
     * Sets which elements are checked in this viewer's tree.
     * The given list contains the elements that are to be checked;
     * all other elements are to be unchecked.
     * Does not fire events to check state listeners.
     * <p>
     * This method is typically used when restoring the interesting
     * state of a viewer captured by an earlier call to <code>getCheckedElements</code>.
     * </p>
     *
     * @param elements the array of checked elements
     * @see #getCheckedElements
     */
    public void setCheckedElements(Object[] elements) {
        assertElementsNotNull(elements);
        CustomHashtable checkedElements = newHashtable(elements.length * 2 + 1);
        for (int i = 0; i < elements.length; ++i) {
            Object element = elements[i];
            internalExpand(element, false);
            checkedElements.put(element, element);
        }
        Control tree = getControl();
        tree.setRedraw(false);
        internalSetChecked(checkedElements, tree);
        tree.setRedraw(true);
    }

    /**
     * Sets the grayed state for the given element in this viewer.
     *
     * @param element the element
     * @param state <code>true</code> if the item should be grayed,
     *  and <code>false</code> if it should be ungrayed
     * @return <code>true</code> if the gray state could be set,
     *  and <code>false</code> otherwise
     */
    public boolean setGrayed(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            ((TreeItem) widget).setGrayed(state);
            return true;
        }
        return false;
    }

    /**
     * Check and gray the selection rather than calling both
     * setGrayed and setChecked as an optimization.
     * Does not fire events to check state listeners.
     * @param element the item being checked
     * @param state a boolean indicating selection or deselection
     * @return boolean indicating success or failure.
     */
    public boolean setGrayChecked(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setChecked(state);
            item.setGrayed(state);
            return true;
        }
        return false;
    }

    /**
     * Sets which elements are grayed in this viewer's tree.
     * The given list contains the elements that are to be grayed;
     * all other elements are to be ungrayed.
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
        CustomHashtable grayedElements = newHashtable(elements.length * 2 + 1);
        for (int i = 0; i < elements.length; ++i) {
            Object element = elements[i];
            internalExpand(element, false);
            grayedElements.put(element, element);
        }
        Control tree = getControl();
        tree.setRedraw(false);
        internalSetGrayed(grayedElements, tree);
        tree.setRedraw(true);
    }

    /**
     * Sets the grayed state for the given element and its parents
     * in this viewer.
     *
     * @param element the element
     * @param state <code>true</code> if the item should be grayed,
     *  and <code>false</code> if it should be ungrayed
     * @return <code>true</code> if the element is visible and the gray
     *  state could be set, and <code>false</code> otherwise
     * @see #setGrayed
     */
    public boolean setParentsGrayed(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setGrayed(state);
            item = item.getParentItem();
            while (item != null) {
                item.setGrayed(state);
                item = item.getParentItem();
            }
            return true;
        }
        return false;
    }

    /**
     * Sets the checked state for the given element and its visible
     * children in this viewer.
     * Assumes that the element has been expanded before. To enforce
     * that the item is expanded, call <code>expandToLevel</code>
     * for the element.
     * Does not fire events to check state listeners.
     *
     * @param element the element
     * @param state <code>true</code> if the item should be checked,
     *  and <code>false</code> if it should be unchecked
     * @return <code>true</code> if the checked state could be set,
     *  and <code>false</code> otherwise
     */
    public boolean setSubtreeChecked(Object element, boolean state) {
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setChecked(state);
            setCheckedChildren(item, state);
            return true;
        }
        return false;
    }

    /**
     * Sets to the given value the checked state for all elements in this viewer.
     * Does not fire events to check state listeners.
     * Assumes that the element has been expanded before. To enforce
     * that the item is expanded, call <code>expandToLevel</code>
     * for the element.
     *
     * @param state <code>true</code> if the element should be checked,
     *  and <code>false</code> if it should be unchecked
     * @deprecated as this method only checks or unchecks visible items
     * is is recommended that {@link #setSubtreeChecked(Object, boolean)}
     * is used instead.
     * @see #setSubtreeChecked(Object, boolean)
     *
     *  @since 3.2
     */
