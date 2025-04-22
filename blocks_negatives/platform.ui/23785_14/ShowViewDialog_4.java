    /**
     * Constructs a new ShowViewDialog.
     * 
     * @param window the workbench window
     * @param viewReg the view registry
     */
    public ShowViewDialog(IWorkbenchWindow window, IViewRegistry viewReg) {
        super(window.getShell());
        this.window = window;
        this.viewReg = viewReg;
    }

    /**
     * This method is called if a button has been pressed.
     */
    @Override
