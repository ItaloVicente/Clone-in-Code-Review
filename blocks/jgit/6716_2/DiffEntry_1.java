	private static void setTreeFilterMarks(DiffEntry entry
			TreeFilter[] filters) throws MissingObjectException
			IncorrectObjectTypeException
		for (int index = 0; index < filters.length; index++) {
			TreeFilter filter = filters[index];
			if (filter != null) {
				try {
					boolean marked = filter.include(walk);
					if (marked)
						entry.treeFilterMarks |= (1L << index);
				} catch (StopWalkException e) {
					filters[index] = null;
				}
			}
		}
	}

