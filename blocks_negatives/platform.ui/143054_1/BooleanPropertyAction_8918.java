    /**
     * Create a new instance of the receiver.
     * @param title The displayable name of the action.
     * @param preferenceStore The preference store to propogate changes to
     * @param property The property that is being updated
     * @throws IllegalArgumentException Thrown if preferenceStore or
     * property are <code>null</code>.
     */
    public BooleanPropertyAction(String title,
            IPreferenceStore preferenceStore, String property)
            throws IllegalArgumentException {
        super(title, AS_CHECK_BOX);
