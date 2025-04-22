                if (entryChanged) {
                    entryChanged = false;
                    updateFromSourceField();
                }

            }
        });

        sourceBrowseButton = new Button(sourceContainerGroup, SWT.PUSH);
        sourceBrowseButton.setText(DataTransferMessages.DataTransfer_browse);
        sourceBrowseButton.addListener(SWT.Selection, this);
        sourceBrowseButton.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL));
        sourceBrowseButton.setFont(parent.getFont());
        setButtonLayoutData(sourceBrowseButton);
    }

    /**
     * Update the receiver from the source name field.
     */

    private void updateFromSourceField() {
