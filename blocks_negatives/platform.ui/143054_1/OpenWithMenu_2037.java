        menuItem.setSelection(isPreferred);
        menuItem.setText(descriptor.getLabel());
        Image image = getImage(descriptor);
        if (image != null) {
            menuItem.setImage(image);
        }
        Listener listener = event -> {
		    switch (event.type) {
		    case SWT.Selection:
		        if (menuItem.getSelection()) {
