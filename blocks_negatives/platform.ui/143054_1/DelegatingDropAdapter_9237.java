    /**
     * Returns the transfer data type supported by the given listener.
     * Returns <code>null</code> if the listener does not support any of the
     * specified data types.
     *
     * @param dataTypes available data types
     * @param listener <code>TransferDropTargetListener</code> to use for testing
     * 	supported data types.
     * @return the transfer data type supported by the given listener or
     * 	<code>null</code>.
     */
    private TransferData getSupportedTransferType(TransferData[] dataTypes,
            TransferDropTargetListener listener) {
        for (TransferData dataType : dataTypes) {
            if (listener.getTransfer().isSupportedType(dataType)) {
                return dataType;
            }
        }
        return null;
    }
