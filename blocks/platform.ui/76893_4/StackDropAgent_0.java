
		hiddenChildren = 0;
		CTabItem item;
		CTabItem[] items = dropCTF.getItems();
		visibleItemms = new ArrayList<>();
		for (int i = 0; i < items.length; i++) {
			item = items[i];
			if (!item.isShowing()) {
				hiddenChildren++;
			} else {
				visibleItemms.add(item);
			}
		}

		items = visibleItemms.toArray(new CTabItem[visibleItemms.size()]);

