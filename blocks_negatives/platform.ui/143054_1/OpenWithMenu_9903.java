        Listener listener = event -> {
		    switch (event.type) {
		    case SWT.Selection:
		        if (menuItem.getSelection()) {
		            IDE.setDefaultEditor(file, null);
		            try {
