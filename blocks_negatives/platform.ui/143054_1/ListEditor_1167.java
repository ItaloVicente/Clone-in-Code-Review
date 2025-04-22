        super.setEnabled(enabled, parent);
        getListControl(parent).setEnabled(enabled);
        addButton.setEnabled(enabled);
        removeButton.setEnabled(enabled);
        upButton.setEnabled(enabled);
        downButton.setEnabled(enabled);
    }

    /**
     * Return the Add button.
     *
     * @return the button
     * @since 3.5
     */
    protected Button getAddButton() {
    	return addButton;
    }

    /**
     * Return the Remove button.
     *
     * @return the button
     * @since 3.5
     */
    protected Button getRemoveButton() {
    	return removeButton;
    }

    /**
     * Return the Up button.
     *
     * @return the button
     * @since 3.5
     */
    protected Button getUpButton() {
    	return upButton;
    }

    /**
     * Return the Down button.
     *
     * @return the button
     * @since 3.5
     */
    protected Button getDownButton() {
    	return downButton;
    }

    /**
     * Return the List.
     *
     * @return the list
     * @since 3.5
     */
    protected List getList() {
    	return list;
    }
