            ISelectionChangedListener listener) {
        selectionChangedListeners.remove(listener);
    }

    /**
     * Sets the selection to a particular object.
     */
    public void setSelection(Object obj) {
        setSelection(new StructuredSelection(obj));
    }

    @Override
