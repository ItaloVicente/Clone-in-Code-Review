		if (!match(raw
			report(MISSING_TREE
		} else if (!checkId(raw)) {
			report(BAD_TREE_SHA1
		}

		while (match(raw
			if (!checkId(raw)) {
				report(BAD_PARENT_SHA1
