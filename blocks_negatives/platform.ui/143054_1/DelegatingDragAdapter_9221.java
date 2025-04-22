    /**
     * Returns the <code>Transfer<code>s from every <code>TransferDragSourceListener</code>.
     *
     * @return the combined <code>Transfer</code>s
     */
    public Transfer[] getTransfers() {
        Transfer[] types = new Transfer[listeners.size()];
        for (int i = 0; i < listeners.size(); i++) {
            TransferDragSourceListener listener = listeners
                    .get(i);
            types[i] = listener.getTransfer();
        }
        return types;
    }
