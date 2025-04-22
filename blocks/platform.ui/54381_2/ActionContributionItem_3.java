			menuCreatorListener = event -> {
				switch (event.type) {
				case SWT.Show:
					handleShowProxy((Menu) event.widget);
					break;
				case SWT.Hide:
					handleHideProxy((Menu) event.widget);
					break;
