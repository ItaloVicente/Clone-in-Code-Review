			for (int i = 0; i < entryCnt; i++)
				if (sortedEntries[i].isSmudged())
					paths.add(sortedEntries[i].getPathString());
			if (paths.isEmpty())
				return;
			walk.setFilter(PathFilterGroup.createFromStrings(paths));

