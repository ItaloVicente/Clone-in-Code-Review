                setAllSelections(true);
                updateWidgetEnablements();
            }
        };
        selectAllButton.addSelectionListener(listener);
        setButtonLayoutData(selectAllButton);

        deselectAllButton = createButton(buttonComposite,
                IDialogConstants.DESELECT_ALL_ID, DESELECT_ALL_TITLE, false);

        listener = new SelectionAdapter() {
            @Override
