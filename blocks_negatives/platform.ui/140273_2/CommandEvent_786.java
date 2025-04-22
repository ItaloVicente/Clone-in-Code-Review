    public ICommand getCommand() {
        return command;
    }

    /**
     * Returns the map of previous attribute values by name.
     *
     * @return the map of previous attribute values by name. This map may be
     *         empty. If this map is not empty, it's collection of keys is
     *         guaranteed to only contain instances of <code>String</code>.
     *         This map is guaranteed to be <code>null</code> if
     *         haveAttributeValuesByNameChanged() is <code>false</code> and is
     *         guaranteed to not be null if haveAttributeValuesByNameChanged()
     *         is <code>true</code>.
     */
