        reuseEditors.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                reuseEditorsThreshold
                        .getLabelControl(editorReuseThresholdGroup).setEnabled(
                                reuseEditors.getSelection());
                reuseEditorsThreshold.getTextControl(editorReuseThresholdGroup)
                        .setEnabled(reuseEditors.getSelection());
            }
        });
