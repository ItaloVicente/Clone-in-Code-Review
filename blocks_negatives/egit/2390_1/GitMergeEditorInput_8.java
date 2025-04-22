
		int kind = Differencer.NO_CHANGE;
		if (conflicting)
			kind = Differencer.CONFLICTING;
		else if (modified)
			kind = Differencer.PSEUDO_CONFLICT;

		DiffNode fileParent = getFileParent(root, file);

		ITypedElement anc;
		if (ancestorCommit != null)
			anc = CompareUtils.getFileRevisionTypedElement(gitPath,
					ancestorCommit, map.getRepository());
		else
			anc = null;
		new DiffNode(fileParent, kind, anc, leftEditable, right);
