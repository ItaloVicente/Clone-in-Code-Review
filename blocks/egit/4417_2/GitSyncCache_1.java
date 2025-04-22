			TreeFilter gsdFilter = gsd.getPathFilter();
			if (filter == null)
				loadDataFromGit(gsd, gsdFilter, repoCache);
			else if (gsdFilter == null)
				loadDataFromGit(gsd, filter, repoCache);
			else
				loadDataFromGit(gsd, AndTreeFilter.create(filter, gsdFilter),
						repoCache);

