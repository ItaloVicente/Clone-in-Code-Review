			getControl().addListener(SWT.SetData, event -> {
				Item item = (Item) event.item;
				final int index = doIndexOf(item);

				if (index == -1) {
					return;
				}
