    /**
     * Create a new instance of the reciever.
     *
     * @param parentShell the parent shell
     * @param activityIds the candidate activities
     * @param strings string overrides
     */
    public EnablementDialog(Shell parentShell, Collection activityIds, Properties strings) {
        super(parentShell);
        this.activityIds = activityIds;
