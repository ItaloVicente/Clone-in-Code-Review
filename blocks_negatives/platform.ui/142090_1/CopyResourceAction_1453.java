    /**
     * Creates a new action with the given text
     *
     * @param provider the shell for any dialogs
     * @param name the string used as the name for the action,
     *   or <code>null</code> if there is no name
     */
    CopyResourceAction(IShellProvider provider, String name){
    	super(name);
        Assert.isNotNull(provider);
        shellProvider = provider;
        initAction();
    }
