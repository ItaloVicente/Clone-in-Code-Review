		if (rules != null) {
			int pOff = pathOffset;
			if (0 < pOff)
				pOff--;
			String p = TreeWalk.pathOf(path, pOff, pLen);
			switch (rules.isIgnored(p, FileMode.TREE.equals(fileMode),
					negatePrevious)) {
			case IGNORED:
				return true;
			case NOT_IGNORED:
				return false;
			case CHECK_PARENT:
				negatePrevious = false;
				break;
			case CHECK_PARENT_NEGATE_FIRST_MATCH:
				negatePrevious = true;
				break;
			}
