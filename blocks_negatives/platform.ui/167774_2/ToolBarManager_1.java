			IContributionItem dest;
			mi = toolBar.getItems();
			int srcIx = 0;
			int destIx = 0;
			for (IContributionItem src : clean) {
				if (srcIx < mi.length) {
					dest = (IContributionItem) mi[srcIx].getData();
				} else {
					dest = null;
				}
