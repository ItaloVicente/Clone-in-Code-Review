		if (FileMode.GITLINK == iMode
				&& FileMode.TREE == wtMode)
			return iMode;
		if (FileMode.TREE == iMode
				&& FileMode.GITLINK == wtMode)
			return iMode;
