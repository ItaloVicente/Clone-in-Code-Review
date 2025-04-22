		super.configureShell(shell);
		shell.setText("Test Browser");
	}

	protected void createActions() {
		fChangeLabelAction = new ChangeLabelAction("Change Label", this);
		fChangeChildLabelAction = new ChangeChildLabelAction(
				"Change Child Label", this);

		fReloadAction = new CreateModelAction("Reload Test Data (small)", this,
				3, 10);
		fReloadActionLarge = new CreateModelAction("Reload Test Data (large)",
				this, 3, 33);
		fReloadActionFlat = new CreateModelAction("Reload Test Data (flat)",
				this, 1, 2000);

		fDeleteAction = new DeleteAction("Delete", this);
		fDeleteChildrenAction = new DeleteChildrenAction("Delete Children",
				this, true);
		fDeleteSomeChildrenAction = new DeleteChildrenAction(
				"Delete Odd Children", this, false);
		fDeleteSiblingsAction = new DeleteSiblingsAction("Delete Siblings",
				this, true);

		fFlushInputAction = new FlushInputAction("Flush Input", this);

		fAddElementAction = new AddElementAction("Add Element to Input", this);
		fAddSiblingAction = new AddSiblingAction("Add Sibling", this);
		fAddSiblingRevealAction = new AddSiblingAction(
				"Add Sibling and Reveal", this, TestModelChange.INSERT
						| TestModelChange.REVEAL);
		fAddSiblingSelectAction = new AddSiblingAction(
				"Add Sibling and Select", this, TestModelChange.INSERT
						| TestModelChange.REVEAL | TestModelChange.SELECT);
		fAddChildAction = new AddChildAction("Add Child", this);
		fAddChildRevealAction = new AddChildAction("Add Child and Reveal",
				this, TestModelChange.INSERT | TestModelChange.REVEAL);
		fAddChildSelectAction = new AddChildAction("Add Child and Select",
				this, TestModelChange.INSERT | TestModelChange.REVEAL
						| TestModelChange.SELECT);

		fWorldChangedAction = new WorldChangedAction("World Changed", this);

		fSetLabelProvider = new SetLabelProviderAction(
				"Set Custom Label Provider", this);

		fAddFilterAction = new AddFilterAction("Add Filter", this);
		fResetFilters = new ResetFilterAction("Reset All Filters", this);

		fSetSorter = new SetSorterAction("Set Sorter", this);
		fResetSorter = new ResetSorterAction("Reset Sorter", this);

		fClearSelection = new ClearSelectionAction("Clear Selection", this);
	}

	@Override
