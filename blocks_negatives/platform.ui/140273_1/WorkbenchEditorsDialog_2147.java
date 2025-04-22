        invertSelection.setFont(font);
        setButtonLayoutData(invertSelection);

        allSelection = new Button(selectionButtons, SWT.PUSH);
        allSelection.setText(WorkbenchMessages.WorkbenchEditorsDialog_allSelection);
        allSelection.addSelectionListener(widgetSelectedAdapter(e -> {
		    editorsTable.setSelection(editorsTable.getItems());
		    updateButtons();
