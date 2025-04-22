		}
	};

	public NewWizardDropDownAction(IWorkbenchWindow window) {
		this(window, ActionFactory.NEW.create(window), ContributionItemFactory.NEW_WIZARD_SHORTLIST.create(window));
	}

	public NewWizardDropDownAction(IWorkbenchWindow window, ActionFactory.IWorkbenchAction showDlgAction,
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

		ISharedImages sharedImages = window.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));

		setMenuCreator(menuCreator);
	}

	@Override
