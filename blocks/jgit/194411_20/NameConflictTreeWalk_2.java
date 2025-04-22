
		int i = getFirstNonEofTreeIndex();
		if(i == -1) {
			allTreesNamesMatchFastMinRef = true;
			return trees[trees.length - 1];
		}
