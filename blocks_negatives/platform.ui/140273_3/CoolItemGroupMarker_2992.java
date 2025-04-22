    /**
     * Create a new group marker with the given name.
     * The group name must not be <code>null</code> or the empty string.
     * The group name is also used as the item id.
     *
     * Note that CoolItemGroupMarkers must have a group name and the name must
     * be unique.
     *
     * @param groupName the name of the group
     */
    public CoolItemGroupMarker(String groupName) {
        super(groupName);
    }
