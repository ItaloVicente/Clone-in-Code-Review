    /**
     * Create a new group marker with the given name.
     * The group name must not be <code>null</code> or the empty string.
     * The group name is also used as the item id.
     *
     * @param groupName the name of the group
     */
    protected AbstractGroupMarker(String groupName) {
        super(groupName);
        Assert.isTrue(groupName != null && groupName.length() > 0);
    }
