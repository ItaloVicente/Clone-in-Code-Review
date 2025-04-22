    /**
     * Creates a new instance of the receiver.
     *
     * @param descriptors the choice of descriptors
     */
    public WorkingSetNewWizard(WorkingSetDescriptor[] descriptors) {
        super();
        Assert.isTrue(descriptors != null && descriptors.length > 0);
        this.descriptors= descriptors;
        setWindowTitle(WorkbenchMessages.WorkingSetNewWizard_title);
    }
