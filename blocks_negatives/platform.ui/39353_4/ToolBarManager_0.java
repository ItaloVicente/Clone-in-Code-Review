
		if (isDirty() || force) {

			if (toolBarExist()) {

				int oldCount = toolBar.getItemCount();

				IContributionItem[] items = getItems();
				ArrayList<IContributionItem> clean = new ArrayList<IContributionItem>(items.length);
				IContributionItem separator = null;
				for (IContributionItem ci : items) {
					if (!isChildVisible(ci)) {
						continue;
					}
					if (ci.isSeparator()) {
						separator = ci;
					} else {
						if (separator != null) {
							if (clean.size() > 0) {
								clean.add(separator);
							}
							separator = null;
						}
						clean.add(ci);
					}
				}

				ToolItem[] mi = toolBar.getItems();
				ArrayList<ToolItem> toRemove = new ArrayList<ToolItem>(mi.length);
				for (ToolItem item : mi) {
					if (item == null)
						continue;
					
					Object data = item.getData();
					if (data == null
							|| !clean.contains(data)
							|| (data instanceof IContributionItem && ((IContributionItem) data)
									.isDynamic())) {
						toRemove.add(item);
					}
				}
