		ObjectInserter inserter = db.newObjectInserter();
			try {
			Tree current = new Tree(db);
			Stack<Tree> trees = new Stack<Tree>();
			trees.push(current);
			String[] prevName = new String[0];
			for (Entry e : entries.values()) {
				if (e.getStage() != 0)
					continue;
				String[] newName = splitDirPath(e.getName());
				int c = longestCommonPath(prevName
				while (c < trees.size() - 1) {
					current.setId(inserter.insert(Constants.OBJ_TREE
					trees.pop();
					current = trees.isEmpty() ? null : (Tree) trees.peek();
				}
				while (trees.size() < newName.length) {
					if (!current.existsTree(newName[trees.size() - 1])) {
						current = new Tree(current
						current.getParent().addEntry(current);
						trees.push(current);
					} else {
						current = (Tree) current.findTreeMember(newName[trees
								.size() - 1]);
						trees.push(current);
					}
