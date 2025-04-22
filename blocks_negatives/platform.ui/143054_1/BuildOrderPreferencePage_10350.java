                removeSelection();
            }
        };
        removeButton.addSelectionListener(listener);
        removeButton.setEnabled(enableComposite);
        removeButton.setFont(font);
        setButtonLayoutData(removeButton);

    }

    /**
     * Create the field for the maximum number of iterations in the presence
     * of cycles.
     */
    private void createMaxIterationsField(Composite composite) {
        maxItersField = new IntegerFieldEditor(
