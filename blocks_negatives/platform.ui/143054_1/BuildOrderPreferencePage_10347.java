                moveSelectionUp();
            }
        };
        upButton.addSelectionListener(listener);

        Button downButton = new Button(this.buttonComposite, SWT.CENTER
                | SWT.PUSH);
        downButton.setText(DOWN_LABEL);
        downButton.setEnabled(enableComposite);
        listener = new SelectionAdapter() {
            @Override
