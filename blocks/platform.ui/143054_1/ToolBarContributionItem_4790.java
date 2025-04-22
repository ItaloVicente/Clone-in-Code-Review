			}

			if (getUseChevron()) {
				coolItem.addSelectionListener(widgetSelectedAdapter(event -> {
					if (event.detail == SWT.ARROW) {
						handleChevron(event);
					}
