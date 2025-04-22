			menu.addListener(SWT.Show, new Listener() {
				@Override
				public void handleEvent(Event event) {
					if (menu.isDisposed()) {
						return;
					}
					MenuItem[] items = menu.getItems();
					for (int i = 0; i < items.length; i++) {
						items[i].dispose();
					}
					IContributionItem[] contributions = getContributionItems();
					for (int i = 0; i < contributions.length; i++) {
						contributions[i].fill(menu, -1);
					}
					new ActionContributionItem(new OpenDialogAction()).fill(
							menu, -1);
