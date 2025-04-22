    /**
     * The <code>PluginDropAdapter</code> implementation of this
     * <code>ViewerDropAdapter</code> method is used to notify the action that some
     * aspect of the drop operation has changed. Subclasses may override.
     */
    @Override
	public boolean validateDrop(Object target, int operation,
            TransferData transferType) {
        currentTransfer = transferType;
        if (currentTransfer != null
                && PluginTransfer.getInstance()
                        .isSupportedType(currentTransfer)) {
            return true;
        }
        return false;
    }
