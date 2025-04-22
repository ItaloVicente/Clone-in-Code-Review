    /**
     * Returns the combined set of <code>Transfer</code> types of all
     * <code>TransferDropTargetListeners</code>.
     *
     * @return the combined set of <code>Transfer</code> types
     */
    public Transfer[] getTransfers() {
        Transfer[] types = new Transfer[listeners.size()];
        for (int i = 0; i < listeners.size(); i++) {
            TransferDropTargetListener listener = listeners
                    .get(i);
            types[i] = listener.getTransfer();
        }
        return types;
    }
