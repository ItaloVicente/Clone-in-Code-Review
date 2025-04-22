        byte[] check = TYPE_NAME.getBytes();
        super.javaToNative(check, transferData);
    }

    /**
     * Overrides org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(TransferData).
     * Test if the native drop data matches this transfer type.
     *
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(TransferData)
     */
    @Override
