			ViewerSorter lSorter = sorterService.findSorter(sourceOfLvalue, parent, e1, e2);
			ViewerSorter rSorter = sorterService.findSorter(sourceOfRvalue, parent, e1, e2);
			sorter = rSorter;

			if (rSorter == null
					|| (lSorter != null && sourceOfLvalue.getSequenceNumber() < sourceOfRvalue
							.getSequenceNumber())) {
				sorter = lSorter;
