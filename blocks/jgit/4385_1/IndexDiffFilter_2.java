		final int cnt = tw.getTreeCount();
		final int wm = tw.getRawMode(workingTree);
		String path = tw.getPathString();

		if (!tw.isPostOrderTraversal()) {
			if (FileMode.TREE.equals(wm)) {
				copyUntrackedFolders(path);
				untrackedParentFolders.addFirst(path);
			}

			for (int i = 0; i < cnt; i++) {
				int rmode = tw.getRawMode(i);
				if (i != workingTree && rmode != 0
						&& FileMode.TREE.equals(rmode)) {
					untrackedParentFolders.clear();
					break;
				}
			}
		}

