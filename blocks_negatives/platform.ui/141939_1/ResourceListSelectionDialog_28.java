    }

    /**
     * Update the specified item with the new info from the resource
     * descriptor.
     * Create a new table item if there is no item.
     *
     * @param index index of the resource descriptor
     * @param itemPos position of the existing item to update
     * @param itemCount number of items in the resources table widget
     */
    private void updateItem(int index, int itemPos, int itemCount) {
        ResourceDescriptor desc = descriptors[index];
        TableItem item;
        if (itemPos < itemCount) {
            item = resourceNames.getItem(itemPos);
            if (item.getData() != desc) {
                item.setText(desc.label);
                item.setData(desc);
                item.setImage(getImage(desc));
                if (itemPos == 0) {
                    resourceNames.setSelection(0);
                    updateFolders(desc);
                }
            }
        } else {
            item = new TableItem(resourceNames, SWT.NONE);
            item.setText(desc.label);
            item.setData(desc);
            item.setImage(getImage(desc));
            if (itemPos == 0) {
                resourceNames.setSelection(0);
                updateFolders(desc);
            }
        }
        updateOKState(true);
    }

    /**
     * Update the enabled state of the OK button.  To be called when
     * the resource list is updated.
     * @param state the new enabled state of the button
     */
    protected void updateOKState(boolean state) {
    	Button okButton = getButton(IDialogConstants.OK_ID);
    	if(okButton != null && !okButton.isDisposed() && state != okEnabled) {
    		okButton.setEnabled(state);
    		okEnabled = state;
    	}
    }
