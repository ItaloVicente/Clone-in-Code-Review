
		if (c instanceof FilteredRevCommit) {
			return (FilteredRevCommit) c;
		}

		c = walk.lookupCommit(c);

		if (c instanceof FilteredRevCommit) {
			return (FilteredRevCommit) c;
		}

		FilteredRevCommit filteredCommit = new FilteredRevCommit(c
				c.getParents());
		walk.objects.add(filteredCommit);
		return filteredCommit;
