			toolItemListener = event -> {
				switch (event.type) {
				case SWT.Dispose:
					handleWidgetDispose(event);
					break;
				case SWT.Selection:
					Widget ew = event.widget;
					if (ew != null) {
						handleWidgetSelection(event, ((ToolItem) ew)
								.getSelection());
