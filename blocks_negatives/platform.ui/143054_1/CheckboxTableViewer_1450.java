    	if(checkStateProvider != null) {
    		super.preservingSelection(updateCode);
    		return;
    	}

        TableItem[] children = getTable().getItems();
        CustomHashtable checked = newHashtable(children.length * 2 + 1);
        CustomHashtable grayed = newHashtable(children.length * 2 + 1);

        for (TableItem item : children) {
            Object data = item.getData();
            if (data != null) {
                if (item.getChecked()) {
