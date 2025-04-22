			menuItemListener = event -> {
				switch (event.type) {
				case SWT.Dispose:
					handleWidgetDispose(event);
					break;
				case SWT.Selection:
					if (event.widget != null) {
						handleWidgetSelection(event);
