		});
		table.addListener(SWT.EraseItem, event -> {
			Object entry = event.item.getData();
			if (entry instanceof QuickAccessEntry) {
				((QuickAccessEntry) entry).erase(event);
			}
		});
		table.addListener(SWT.PaintItem, event -> {
			Object entry = event.item.getData();
			if (entry instanceof QuickAccessEntry) {
				((QuickAccessEntry) entry).paint(event, textLayout, resourceManager, boldStyle, grayColor);
			}
		});
