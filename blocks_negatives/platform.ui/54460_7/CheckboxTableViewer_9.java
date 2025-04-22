    	if(checkStateProvider != null) {
    		super.preservingSelection(updateCode);
    		return;
    	}

        TableItem[] children = getTable().getItems();
        CustomHashtable checked = newHashtable(children.length * 2 + 1);
        CustomHashtable grayed = newHashtable(children.length * 2 + 1);

        for (int i = 0; i < children.length; i++) {
            TableItem item = children[i];
            Object data = item.getData();
            if (data != null) {
                if (item.getChecked()) {
