		ViewForm form = new ViewForm(parent, SWT.NONE);
		CLabel label = new CLabel(form, SWT.NONE);
		form.setTopLeft(label);
		Object input = getInput();
		label.setText(input.toString());
		if (fPanes == 1) {
			Viewer viewer = createViewer(form);
			form.setContent(viewer.getControl());
			fViewer = viewer;
			setInput((TestElement) input);
		} else if (fPanes == 2) {
			SashForm sashForm = new SashForm(form, SWT.VERTICAL);
			form.setContent(sashForm);
			Viewer viewer = createViewer(sashForm);
			fViewer = viewer;
			viewer.setInput(input);
			viewer = createViewer(sashForm);
			viewer.setInput(input);
		}
		createActions();
		fillMenuBar(getMenuBarManager());
		viewerFillMenuBar(getMenuBarManager());
		getMenuBarManager().updateAll(false);
		return form;
	}

	public abstract Viewer createViewer(Composite parent);

	protected void fillMenuBar(MenuManager mgr) {

		MenuManager setupMenu = new MenuManager("Setup", "Setup");
		mgr.add(setupMenu);
		setupMenu.add(fReloadAction);
		setupMenu.add(fReloadActionLarge);
		setupMenu.add(fReloadActionFlat);
		setupMenu.add(new Separator());
		setupMenu.add(fFlushInputAction);
		setupMenu.add(new Separator());
		setupMenu.add(fSetLabelProvider);
		setupMenu.add(new Separator());
		setupMenu.add(fAddFilterAction);
		setupMenu.add(fResetFilters);
		setupMenu.add(new Separator());
		setupMenu.add(fSetSorter);
		setupMenu.add(fResetSorter);

		MenuManager testMenu = new MenuManager("Tests", "Tests");
		mgr.add(testMenu);
		testMenu.add(fChangeLabelAction);
		testMenu.add(fChangeChildLabelAction);
		testMenu.add(new Separator());

		testMenu.add(fDeleteAction);
		testMenu.add(fDeleteChildrenAction);
		testMenu.add(fDeleteSomeChildrenAction);
		testMenu.add(fDeleteSiblingsAction);
		testMenu.add(new Separator());

		testMenu.add(fAddElementAction);
		testMenu.add(new Separator());

		testMenu.add(fAddSiblingAction);
		testMenu.add(fAddSiblingRevealAction);
		testMenu.add(fAddSiblingSelectAction);
		testMenu.add(new Separator());

		testMenu.add(fAddChildAction);
		testMenu.add(fAddChildRevealAction);
		testMenu.add(fAddChildSelectAction);
		testMenu.add(new Separator());

		testMenu.add(fClearSelection);
		testMenu.add(new Separator());

		testMenu.add(fWorldChangedAction);
	}

	public TestElement getInput() {
		return fInput;
	}

	public Viewer getViewer() {
		return fViewer;
	}

	public Composite getViewerContainer() {
		return null;
	}

	public void open(TestElement input) {
		setInput(input);
		super.open();
	}

	public void setInput(TestElement input) {
		fInput = input;
		if (getViewer() != null) {
