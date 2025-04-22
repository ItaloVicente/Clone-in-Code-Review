		for (int i = 0; i < trees.length; i++) {
			AbstractTreeIterator tree = trees[i];
			if (type.isInstance(tree)) {
				return type.cast(tree);
			}
		}
