                setDirty(dirtyToggle.getSelection());
            }
        });
        dirtyToggle.setSelection(isDirty());

        final Button adaptToggle = new Button(parent, SWT.CHECK);
        adaptToggle.setText("Adapt to resource");
        adaptToggle.addSelectionListener(new SelectionAdapter() {
        	@Override
