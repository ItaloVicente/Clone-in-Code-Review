			menu.addListener(SWT.Show, event -> {
				if (menu.isDisposed()) {
					return;
				}
				MenuItem[] items = menu.getItems();
				for (int i1 = 0; i1 < items.length; i1++) {
					items[i1].dispose();
				}
				IContributionItem[] contributions = getContributionItems();
				for (int i2 = 0; i2 < contributions.length; i2++) {
					contributions[i2].fill(menu, -1);
