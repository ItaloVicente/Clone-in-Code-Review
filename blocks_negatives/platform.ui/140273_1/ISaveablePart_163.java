    /**
     * Returns whether the contents of this part have changed since the last save
     * operation. If this value changes the part must fire a property listener
     * event with <code>PROP_DIRTY</code>.
     * <p>
     * <b>Note:</b> this method is called often on a part open or part
     * activation switch, for example by actions to determine their
     * enabled status.
     * </p>
     *
     * @return <code>true</code> if the contents have been modified and need
     *   saving, and <code>false</code> if they have not changed since the last
     *   save
     */
    boolean isDirty();
