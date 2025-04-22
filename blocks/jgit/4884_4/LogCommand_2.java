		if (skip > -1 && maxCount > -1)
			walk.setRevFilter(AndRevFilter.create(SkipRevFilter.create(skip)
					MaxCountRevFilter.create(maxCount)));
		else if (skip > -1)
			walk.setRevFilter(SkipRevFilter.create(skip));
		else if (maxCount > -1)
