                moveSelectionDown();
            }
        };
        downButton.addSelectionListener(listener);
        downButton.setFont(font);
        setButtonLayoutData(downButton);

        Button addButton = new Button(this.buttonComposite, SWT.CENTER
                | SWT.PUSH);
        addButton.setText(ADD_LABEL);
        listener = new SelectionAdapter() {
            @Override
