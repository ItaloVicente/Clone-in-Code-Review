			return false;

		/*
		 * Boolean matched is necessary because we may have encountered
		 * a negation ("!/test.c").
		 */

		int i;
		for (i = rules.size() -1; i > -1; i--) {
			matched = rules.get(i).isMatch(tar, targetFile.isDirectory());
			if (matched)
				break;
