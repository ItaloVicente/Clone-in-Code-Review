		ITypedElement right;
		if (conflicting)
			right = CompareUtils.getFileRevisionTypedElement(gitPath,
					rightCommit, map.getRepository());
		else
			right = CompareUtils.getFileRevisionTypedElement(gitPath,
					headCommit, map.getRepository());
