        return new String[] { TYPE_NAME };
    }

    /**
     * Overrides org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(Object,
     * TransferData).
     * Only encode the transfer type name since the selection is read and
     * written in the same process.
     *
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(java.lang.Object, org.eclipse.swt.dnd.TransferData)
     */
    @Override
