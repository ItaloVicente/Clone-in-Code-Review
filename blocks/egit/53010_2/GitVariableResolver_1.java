		if (mapping != null) {
			File workTree = mapping.getWorkTree();
			if (workTree != null) {
				return workTree.getAbsolutePath();
			}
		}
		return ""; //$NON-NLS-1$
