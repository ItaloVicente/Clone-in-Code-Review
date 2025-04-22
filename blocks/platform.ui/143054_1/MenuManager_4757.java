							separator = null;
						}
						clean.add(ci);
					}
				}

				Item[] mi = getMenuItems();

				for (Item element : mi) {
					Object data = element.getData();

					if (data == null || !clean.contains(data)) {
						element.dispose();
					} else if (data instanceof IContributionItem && ((IContributionItem) data).isDynamic()
							&& ((IContributionItem) data).isDirty()) {
						element.dispose();
					}
				}

				mi = getMenuItems();
				int srcIx = 0;
				int destIx = 0;

				for (IContributionItem src : clean) {
					IContributionItem dest;

					if (srcIx < mi.length) {
