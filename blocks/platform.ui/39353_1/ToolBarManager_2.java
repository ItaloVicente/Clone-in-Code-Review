			Object data = item.getData();
			if (data == null || !clean.contains(data)
					|| (data instanceof IContributionItem && ((IContributionItem) data).isDynamic())) {
				toRemove.add(item);
			}
		}
