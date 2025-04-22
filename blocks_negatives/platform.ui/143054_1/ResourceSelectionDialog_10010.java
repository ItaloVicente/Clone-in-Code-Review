    /**
     * @param event the event
     */
    public void checkStateChanged(CheckStateChangedEvent event) {
        getOkButton().setEnabled(selectionGroup.getCheckedElementCount() > 0);
    }
