        lastClickedItem = null;
        if (event.detail == SWT.CHECK) {
            TreeItem item = (TreeItem) event.item;
            lastClickedItem = item;
            super.handleSelect(event);

            Object data = item.getData();
            if (data != null) {
                fireCheckStateChanged(new CheckStateChangedEvent(this, data,
                        item.getChecked()));
            }
        } else {
