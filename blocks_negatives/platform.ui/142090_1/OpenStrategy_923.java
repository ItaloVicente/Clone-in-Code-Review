        }
    }

    /*
     * Fire the open event to all openEventListeners
     */
    private void fireOpenEvent(SelectionEvent e) {
        if (e.item != null && e.item.isDisposed()) {
