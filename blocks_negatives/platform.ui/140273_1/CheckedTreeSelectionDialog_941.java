        selectButton.addSelectionListener(listener);
        Button deselectButton = createButton(buttonComposite,
                IDialogConstants.DESELECT_ALL_ID, WorkbenchMessages.CheckedTreeSelectionDialog_deselect_all,
                false);
        listener = widgetSelectedAdapter(e -> {
		    fViewer.setCheckedElements(new Object[0]);
		    updateOKStatus();
