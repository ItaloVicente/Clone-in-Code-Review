		((Tree) viewer.getControl()).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent event) {
				ISelection selection = viewer.getSelection();
				if (selection.isEmpty()) {
					return;
				}
				IPreferenceNode singleSelection = getSingleSelection(selection);
				boolean expanded = viewer.getExpandedState(singleSelection);
				viewer.setExpandedState(singleSelection, !expanded);
