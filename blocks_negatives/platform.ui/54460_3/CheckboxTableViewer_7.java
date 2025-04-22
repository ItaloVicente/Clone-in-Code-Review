        if (event.detail == SWT.CHECK) {

            TableItem item = (TableItem) event.item;
            Object data = item.getData();
            if (data != null) {
                fireCheckStateChanged(new CheckStateChangedEvent(this, data,
                        item.getChecked()));
            }
        } else {
