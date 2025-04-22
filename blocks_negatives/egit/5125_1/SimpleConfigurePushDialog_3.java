		refSpecGroup.setLayout(new GridLayout(5, false));

		ExpandableComposite advanced = new ExpandableComposite(refSpecGroup,
				ExpandableComposite.TREE_NODE
						| ExpandableComposite.CLIENT_INDENT);
		if (advancedMode)
			advanced.setExpanded(true);
		advanced.setText(UIText.SimpleConfigurePushDialog_AdvancedButton);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL)
				.span(5, 1).grab(true, false).applyTo(advanced);
		advanced.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				Activator.getDefault().getPreferenceStore().setValue(
						ADVANCED_MODE_PREFERENCE, e.getState());
				GridData data = (GridData) changeRefSpec.getLayoutData();
				data.exclude = !e.getState();
				changeRefSpec.setVisible(!data.exclude);
				refSpecGroup.layout(true);
			}
		});

		Label refSpecLabel = new Label(refSpecGroup, SWT.NONE);
		refSpecLabel.setText(UIText.SimpleConfigurePushDialog_RefSpecLabel);
		GridDataFactory.fillDefaults().span(5, 1).applyTo(refSpecLabel);
