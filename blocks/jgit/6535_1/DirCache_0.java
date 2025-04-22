			for (int i = 0; i < entryCnt; i++)
				if (sortedEntries[i].isSmudged())
					pathes.add(sortedEntries[i].getPathString());
			if (pathes.isEmpty())
				return;
			walk.setFilter(PathFilterGroup.createFromStrings(pathes));

