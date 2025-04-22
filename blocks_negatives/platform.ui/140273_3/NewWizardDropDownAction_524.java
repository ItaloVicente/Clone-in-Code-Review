        }
    };

    /**
     * Create a new <code>NewWizardDropDownAction</code>, with the default
     * action for opening the new wizard dialog, and the default contribution item
     * for populating the drop-down menu.
     *
     * @param window the window in which this action appears
     */
    public NewWizardDropDownAction(IWorkbenchWindow window) {
        this(window, ActionFactory.NEW.create(window), ContributionItemFactory.NEW_WIZARD_SHORTLIST.create(window));
    }

    /**
     * Create a new <code>NewWizardDropDownAction</code>.
     *
     * @param window the window in which this action appears
     * @param showDlgAction the action to delegate to when this action is run directly,
     *   rather than being dropped down
     * @param newWizardMenu the contribution item that adds the contents to the drop-down menu
     */
    public NewWizardDropDownAction(IWorkbenchWindow window,
            ActionFactory.IWorkbenchAction showDlgAction,
            IContributionItem newWizardMenu) {
        super(WorkbenchMessages.NewWizardDropDown_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        this.showDlgAction = showDlgAction;
        this.newWizardMenu = newWizardMenu;
        tracker = new PerspectiveTracker(window, this);

        setToolTipText(showDlgAction.getToolTipText());

        ISharedImages sharedImages = window.getWorkbench()
                .getSharedImages();
        setImageDescriptor(sharedImages
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
        setDisabledImageDescriptor(sharedImages
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));

        setMenuCreator(menuCreator);
    }


    @Override
