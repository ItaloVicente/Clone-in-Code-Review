				if (page != null && page.getAggregateWorkingSet() != null) {
					IContributionItem item = new WorkingSetMenuContributionItem(
							++mruMenuCount,
							WorkingSetFilterActionGroup.this, page.getAggregateWorkingSet());
					items.add(item);
				}
