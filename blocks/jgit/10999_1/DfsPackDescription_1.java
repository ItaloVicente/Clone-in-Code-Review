		PackSource as = getPackSource();
		PackSource bs = b.getPackSource();
		if (as != null && bs != null) {
			int cmp = as.category - bs.category;
			if (cmp != 0)
				return cmp;
		}

