        buttonComposite.setFont(composite.getFont());
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
                | GridData.GRAB_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        buttonComposite.setLayoutData(data);
        Button selectButton = createButton(buttonComposite,
                IDialogConstants.SELECT_ALL_ID, WorkbenchMessages.CheckedTreeSelectionDialog_select_all,
                false);
        SelectionListener listener = widgetSelectedAdapter(e -> {
		    Object[] viewerElements = fContentProvider.getElements(fInput);
		    if (fContainerMode) {
