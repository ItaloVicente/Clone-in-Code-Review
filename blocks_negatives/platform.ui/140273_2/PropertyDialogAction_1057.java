    /**
     * Creates a new action for opening a property dialog on the elements from
     * the given selection provider.
     *
     * @param shell
     *            provides the shell in which the dialog will open
     * @param provider
     *            the selection provider whose elements the property dialog will
     *            describe
     * @since 3.1
     */
    public PropertyDialogAction(IShellProvider shell, ISelectionProvider provider) {
        super(provider, WorkbenchMessages.PropertyDialog_text);
        Assert.isNotNull(shell);
        this.shellProvider = shell;
        setToolTipText(WorkbenchMessages.PropertyDialog_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IWorkbenchHelpContextIds.PROPERTY_DIALOG_ACTION);
    }
