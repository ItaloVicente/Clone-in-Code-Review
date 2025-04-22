                addProject();
            }
        };
        addButton.addSelectionListener(listener);
        addButton.setEnabled(enableComposite);
        addButton.setFont(font);
        setButtonLayoutData(addButton);

        Button removeButton = new Button(this.buttonComposite, SWT.CENTER
                | SWT.PUSH);
        removeButton.setText(REMOVE_LABEL);
        listener = new SelectionAdapter() {
            @Override
