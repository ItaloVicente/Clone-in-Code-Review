
    private static final int TYPEID = registerType(TYPE_NAME);

    private static final LocalSelectionTransfer INSTANCE = new LocalSelectionTransfer();

    private ISelection selection;

    private long selectionSetTime;

    /**
     * Only the singleton instance of this class may be used.
     */
    protected LocalSelectionTransfer() {
    }

    /**
     * Returns the singleton.
     *
     * @return the singleton
     */
    public static LocalSelectionTransfer getTransfer() {
        return INSTANCE;
    }

    /**
     * Returns the local transfer data.
     *
     * @return the local transfer data
     */
    public ISelection getSelection() {
        return selection;
    }

    /**
     * Tests whether native drop data matches this transfer type.
     *
     * @param result result of converting the native drop data to Java
     * @return true if the native drop data does not match this transfer type.
     * 	false otherwise.
     */
    private boolean isInvalidNativeType(Object result) {
        return !(result instanceof byte[])
                || !TYPE_NAME.equals(new String((byte[]) result));
    }

    /**
     * Returns the type id used to identify this transfer.
     *
     * @return the type id used to identify this transfer.
     */
    @Override
