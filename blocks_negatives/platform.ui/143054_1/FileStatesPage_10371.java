        return text;
    }

    private Button addCheckBox(String label, boolean selected, Composite parent) {
    	Button button = new Button(parent, SWT.CHECK | SWT.LEFT);
        button.addListener(SWT.Selection, this);
        GridData data = new GridData();
        data.horizontalSpan = 2;
        data.horizontalIndent= -INDENT;
        button.setLayoutData(data);
        button.setText(label);
        button.setSelection(selected);
        return button;
    }

    /**
     * Recomputes the page's error state by validating all
     * the fields.
     */
    private void checkState() {
        if (longevityText == null || maxStatesText == null || maxStateSizeText == null
        		|| applyPolicyButton == null) {
            setValid(false);
            return;
        }

        boolean newState= applyPolicyButton.getSelection();
