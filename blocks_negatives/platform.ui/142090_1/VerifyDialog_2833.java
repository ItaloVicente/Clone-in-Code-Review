                radio.setSelection(true);
            }
        }
    }

    /*
     * Initializes the checklist with empty checks.
     */
    private void createCheckListGroup(Composite parent) {
        Group group = new Group(parent, SWT.SHADOW_NONE);
        group.setText("Verify that:");
        group.setLayout(new GridLayout());
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        group.setLayoutData(data);

        int checkListSize = 0;
        for (int i = 0; i < _dialogTests.length; i++) {
            int size = _dialogTests[i].checkListTexts().size();
            if (size > checkListSize) {
                checkListSize = size;
            }
        }
        _checkList = new Button[checkListSize];
        SelectionAdapter selectionAdapter = new SelectionAdapter() {
            @Override
