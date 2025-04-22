        Listener listener = event -> {
		    switch (event.type) {
		    case SWT.Selection:
		        if (menuItem.getSelection()) {
					openEditor(descriptor, false);
				}
		        break;
		    }
		};
