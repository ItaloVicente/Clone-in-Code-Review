
		if (!checkMaybeDifferentInBloomFilter(c)) {
			c.flags |= rewriteFlag;
			if (c.parents.length > 1) {
				c.parents = new RevCommit[] { c.parents[0] };
			}
			return false;
		}
