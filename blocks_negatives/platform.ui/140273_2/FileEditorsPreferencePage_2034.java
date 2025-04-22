        TableItem item = new TableItem(resourceTypeTable, SWT.NULL, index);
        if (image != null) {
            item.setImage(image);
        }
        item.setText(mapping.getLabel());
        item.setData(mapping);
        if (selected) {
            resourceTypeTable.setSelection(index);
        }

        return item;
    }

    /**
     * This is a hook for sublcasses to do special things when the ok
     * button is pressed.
     * For example reimplement this method if you want to save the
     * page's data into the preference bundle.
     */
    @Override
