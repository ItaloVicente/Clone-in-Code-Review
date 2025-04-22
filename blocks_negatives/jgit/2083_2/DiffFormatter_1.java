
			filter = TreeFilter.ANY_DIFF;
			if (a instanceof WorkingTreeIterator)
				filter = AndTreeFilter.create(new NotIgnoredFilter(0), filter);
			if (b instanceof WorkingTreeIterator)
				filter = AndTreeFilter.create(new NotIgnoredFilter(1), filter);
