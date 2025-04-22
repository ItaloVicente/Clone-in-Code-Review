    public AddTaskAction(IShellProvider provider) {
    	super(IDEWorkbenchMessages.AddTaskLabel);
        Assert.isNotNull(provider);
        shellProvider = provider;
        initAction();
    }
