                selectionGroup.setAllSelections(true);
            }
        };
        selectButton.addSelectionListener(listener);

        Button deselectButton = new Button(buttonComposite, SWT.PUSH);
        deselectButton.setText(DESELECT_ALL_TITLE);
        listener = new SelectionAdapter() {
            @Override
