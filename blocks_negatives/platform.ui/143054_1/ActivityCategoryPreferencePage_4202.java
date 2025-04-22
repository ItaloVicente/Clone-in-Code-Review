        enableAll.setText(ActivityMessages.ActivityEnabler_selectAll);
        setButtonLayoutData(enableAll);

        Button disableAll = new Button(composite, SWT.PUSH);
        disableAll.addSelectionListener(widgetSelectedAdapter(e -> workingCopy.setEnabledActivityIds(Collections.EMPTY_SET)));
        disableAll.setText(ActivityMessages.ActivityEnabler_deselectAll);
        setButtonLayoutData(disableAll);

    }

    /**
     * @param parent
     */
    private void createDetailsArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 0;
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        new Label(composite, SWT.NONE).setText(ActivityMessages.ActivityEnabler_description);
        descriptionText = new Text(composite, SWT.WRAP | SWT.READ_ONLY | SWT.BORDER);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.heightHint = 100;
        data.widthHint = 200;
        descriptionText.setLayoutData(data);

        new Label(composite, SWT.NONE).setText(ActivityMessages.ActivitiesPreferencePage_requirements);
        dependantViewer = new TableViewer(composite, SWT.BORDER);
        dependantViewer.getControl().setLayoutData(
                new GridData(GridData.FILL_BOTH));
        dependantViewer.setContentProvider(new CategoryContentProvider());
        dependantViewer.addFilter(new EmptyCategoryFilter());
        dependantViewer.setLabelProvider(new CategoryLabelProvider(false));
        dependantViewer.setInput(Collections.EMPTY_SET);
    }

    /**
     * @param parent
     */
    private void createCategoryArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 0;
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        composite.setLayout(layout);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = 200;
        composite.setLayoutData(data);
        Label label = new Label(composite, SWT.NONE);
        label.setText(strings.getProperty(CATEGORY_NAME, ActivityMessages.ActivityEnabler_categories));
        Table table = new Table(composite, SWT.CHECK | SWT.BORDER | SWT.SINGLE);
        table.addSelectionListener(widgetSelectedAdapter(e -> {
        	if (e.detail == SWT.CHECK) {
        		TableItem tableItem = (TableItem) e.item;

        		ICategory category = (ICategory) tableItem.getData();
        		if (isLocked(category)) {
        			tableItem.setChecked(true);
        			return;
        		}
				Set activitySet = WorkbenchActivityHelper.getActivityIdsForCategory(category);
        		if (tableItem.getChecked()) {
        			activitySet.addAll(workingCopy.getEnabledActivityIds());
        		} else {
        			HashSet newSet = new HashSet(workingCopy.getEnabledActivityIds());
        			newSet.removeAll(activitySet);
        			activitySet = newSet;
        		}

        		workingCopy.setEnabledActivityIds(activitySet);
        	}
        }));
        categoryViewer = new CheckboxTableViewer(table);
