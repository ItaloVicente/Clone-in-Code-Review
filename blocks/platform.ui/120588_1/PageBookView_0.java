			IPage currentPage = getCurrentPage();
			PageRec activePageRec = getPageRec(currentPage);
			if (activePageRec != null && mapPartToRec != null) {
				if (currentPage != null) {
					if (activePageRec.subActionBars != null) {
						activePageRec.subActionBars.activate();
					}
				}
			}
