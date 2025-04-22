        }
    }

    /*
     * Fire the post selection event to all postSelectionEventListeners
     */
    private void firePostSelectionEvent(SelectionEvent e) {
        if (e.item != null && e.item.isDisposed()) {
